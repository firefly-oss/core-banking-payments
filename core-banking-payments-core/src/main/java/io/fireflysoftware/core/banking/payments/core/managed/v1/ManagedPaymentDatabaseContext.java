/*
 * Copyright 2026 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fireflysoftware.core.banking.payments.core.managed.v1;

import static io.fireflysoftware.core.banking.payments.core.managed.v1.ManagedPaymentPolicy.*;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.reactive.TransactionSynchronizationManager;

import reactor.core.publisher.Mono;

import java.util.*;

@Component
public final class ManagedPaymentDatabaseContext {
    private final DatabaseClient db;

    public ManagedPaymentDatabaseContext(DatabaseClient db) {
        this.db = db;
    }

    Mono<Void> establish(Call call, String tenant, String purpose, UUID... accounts) {
        return establish(
                call,
                tenant,
                purpose,
                "CREATE".equals(purpose) ? "payments:create" : "payments:read",
                accounts);
    }

    Mono<Void> establish(Call call, String tenant, String purpose, String scope, UUID... accounts) {
        return TransactionSynchronizationManager.forCurrentTransaction()
                .onErrorMap(
                        NoTransactionException.class,
                        error -> fail(503, "NATIVE_PAYMENT_TRANSACTION_REQUIRED"))
                .flatMap(
                        manager -> {
                            if (!manager.isActualTransactionActive()
                                    || !manager.hasResource(db.getConnectionFactory()))
                                return Mono.error(fail(503, "NATIVE_PAYMENT_TRANSACTION_REQUIRED"));
                            return Mono.defer(
                                    () -> establishBound(call, tenant, purpose, scope, accounts));
                        });
    }

    private Mono<Void> establishBound(
            Call call, String tenant, String purpose, String scope, UUID... accounts) {
        require(call, tenant, scope, accounts);
        if (!Set.of("CREATE", "READ", "MUTATE", "OBSERVE").contains(purpose))
            return Mono.error(fail(403, "NATIVE_PAYMENT_PURPOSE_REQUIRED"));
        String resources =
                Arrays.stream(accounts)
                        .distinct()
                        .map(a -> "\"" + a + "\"")
                        .collect(java.util.stream.Collectors.joining(",", "[", "]"));
        return db.sql(
                        "SELECT NOT r.rolsuper AND NOT r.rolbypassrls AND NOT"
                            + " pg_catalog.has_schema_privilege(current_user,'public','CREATE') AND"
                            + " NOT EXISTS(SELECT 1 FROM pg_catalog.pg_class c JOIN"
                            + " pg_catalog.pg_namespace n ON n.oid=c.relnamespace WHERE"
                            + " n.nspname='public' AND c.relname LIKE 'managed_payment_%' AND"
                            + " pg_catalog.pg_get_userbyid(c.relowner)=current_user) AS allowed"
                            + " FROM pg_catalog.pg_roles r WHERE r.rolname=current_user")
                .map((row, m) -> Boolean.TRUE.equals(row.get("allowed", Boolean.class)))
                .one()
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(fail(503, "NONPRIVILEGED_PAYMENT_RUNTIME_REQUIRED")))
                .then(
                        db.sql(
                                        "SELECT"
                                            + " set_config('firefly.payment.tenant',:t,true),set_config('firefly.payment.accounts',:a,true),set_config('firefly.payment.purpose',:p,true),set_config('firefly.payment.xid',pg_current_xact_id()::text,true),set_config('firefly.payment.until',:until,true)")
                                .bind("t", tenant)
                                .bind("a", resources)
                                .bind("p", purpose)
                                .bind(
                                        "until",
                                        Long.toString(
                                                ((java.time.Instant)
                                                                call.principal()
                                                                        .claims()
                                                                        .get("exp"))
                                                        .toEpochMilli()))
                                .fetch()
                                .rowsUpdated())
                .then();
    }
}
