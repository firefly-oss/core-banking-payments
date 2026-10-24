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

package io.fireflysoftware.core.banking.payments.core.services.closure.v1;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.reactive.TransactionSynchronizationManager;

import reactor.core.publisher.Mono;

@Component
public final class PaymentAccountQuiescenceDatabaseContext {
    private final DatabaseClient db;

    public PaymentAccountQuiescenceDatabaseContext(DatabaseClient db) {
        this.db = db;
    }

    Mono<Void> establish(PaymentAccountQuiescenceAuthority.Admission admission) {
        return TransactionSynchronizationManager.forCurrentTransaction()
                .onErrorMap(
                        NoTransactionException.class,
                        e -> PaymentAccountQuiescenceStore.unavailable())
                .flatMap(
                        manager -> {
                            if (!manager.isActualTransactionActive()
                                    || !manager.hasResource(db.getConnectionFactory()))
                                return Mono.error(PaymentAccountQuiescenceStore.unavailable());
                            admission.requireCurrent();
                            return db.sql(
                                            """
                                            WITH protected(name) AS (VALUES ('managed_payment_operation'),('managed_payment_action'),('managed_payment_effect'),
                                            ('managed_payment_projection'),('managed_payment_history'),('managed_payment_outbox'),('managed_payment_account_gate'),
                                            ('managed_payment_account_fence'),('managed_payment_account_action'),('managed_payment_account_history'),('managed_payment_account_outbox'),
                                            ('managed_payment_account_observation'),('managed_payment_account_observation_member'),('managed_payment_account_observation_reference')),
                                            relations AS (SELECT c.* FROM protected p JOIN pg_class c ON c.relname=p.name JOIN pg_namespace n ON n.oid=c.relnamespace AND n.nspname='public')
                                            SELECT NOT r.rolsuper AND NOT r.rolbypassrls AND NOT r.rolcreaterole AND NOT r.rolcreatedb
                                            AND NOT has_schema_privilege(current_user,'public','CREATE') AND NOT pg_has_role(current_user,'payment_closure_guard','MEMBER')
                                            AND (SELECT count(*)=14 AND bool_and(relrowsecurity AND relforcerowsecurity AND NOT pg_has_role(current_user,relowner,'MEMBER')) FROM relations)
                                            AND EXISTS(SELECT 1 FROM pg_roles g WHERE g.rolname='payment_closure_guard' AND NOT g.rolsuper AND NOT g.rolbypassrls AND NOT g.rolcanlogin AND NOT g.rolcreaterole AND NOT g.rolcreatedb AND NOT g.rolinherit)
                                            AND EXISTS(SELECT 1 FROM pg_proc p WHERE p.oid='public.managed_payment_account_capture(text,uuid,uuid,uuid)'::regprocedure
                                              AND p.prosecdef AND p.proowner='payment_closure_guard'::regrole AND p.proconfig @> ARRAY['search_path=pg_catalog, public, pg_temp'])
                                            AND :until>floor(extract(epoch FROM clock_timestamp())*1000)::bigint AS allowed
                                            FROM pg_roles r WHERE r.rolname=current_user
                                            """)
                                    .bind("until", admission.validUntilEpochMillis())
                                    .map(
                                            (r, m) ->
                                                    Boolean.TRUE.equals(
                                                            r.get("allowed", Boolean.class)))
                                    .one()
                                    .filter(Boolean::booleanValue)
                                    .switchIfEmpty(
                                            Mono.error(PaymentAccountQuiescenceStore.unavailable()))
                                    .then(
                                            Mono.defer(
                                                    () -> {
                                                        admission.requireCurrent();
                                                        return db.sql(
                                                                        """
                                                                        SELECT set_config('firefly.payment_closure.tenant',:t,true),set_config('firefly.payment_closure.account',:a,true),
                                                                        set_config('firefly.payment_closure.purpose',:p,true),set_config('firefly.payment_closure.xid',pg_current_xact_id()::text,true),
                                                                        set_config('firefly.payment_closure.until',:until,true),set_config('firefly.payment_closure.actor_issuer',:issuer,true),
                                                                        set_config('firefly.payment_closure.actor_subject',:subject,true)
                                                                        """)
                                                                .bind("t", admission.tenantId())
                                                                .bind(
                                                                        "a",
                                                                        admission
                                                                                .accountId()
                                                                                .toString())
                                                                .bind("p", admission.operation())
                                                                .bind(
                                                                        "until",
                                                                        Long.toString(
                                                                                admission
                                                                                        .validUntilEpochMillis()))
                                                                .bind(
                                                                        "issuer",
                                                                        admission.actor().issuer())
                                                                .bind(
                                                                        "subject",
                                                                        admission.actor().subject())
                                                                .fetch()
                                                                .rowsUpdated()
                                                                .then();
                                                    }));
                        });
    }
}
