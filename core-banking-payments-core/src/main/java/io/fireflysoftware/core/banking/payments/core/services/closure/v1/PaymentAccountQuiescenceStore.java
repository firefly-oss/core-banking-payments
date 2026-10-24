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

import static io.fireflysoftware.core.banking.payments.interfaces.dtos.closure.v1.PaymentAccountQuiescenceContracts.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.http.HttpStatus;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.*;

import java.util.*;

@Component
public final class PaymentAccountQuiescenceStore {
    private final DatabaseClient db;
    private final ObjectMapper json = new ObjectMapper().registerModule(new JavaTimeModule());

    public PaymentAccountQuiescenceStore(DatabaseClient db) {
        this.db = db;
    }

    public record Action(
            String kind, String digest, Selection selection, String request, String receipt) {}

    public record Fence(
            UUID fenceId,
            UUID closureId,
            Selection selection,
            FenceState state,
            long version,
            QuiescenceReceipt acceptance) {}

    <T> T decode(String value, Class<T> type) {
        try {
            return json.readValue(value, type);
        } catch (Exception e) {
            throw unavailable();
        }
    }

    String encode(Object value) {
        try {
            return json.writeValueAsString(value);
        } catch (Exception e) {
            throw unavailable();
        }
    }

    Mono<Void> accountGate(String tenant, UUID account) {
        return db.sql("SELECT public.managed_payment_account_gate(:t,:a)")
                .bind("t", tenant)
                .bind("a", account)
                .fetch()
                .rowsUpdated()
                .then();
    }

    Mono<Action> action(String tenant, UUID account, UUID action) {
        return db.sql(
                        "SELECT kind,intent_digest,selection::text,request::text,receipt::text FROM"
                            + " managed_payment_account_action WHERE tenant_id=:t AND account_id=:a"
                            + " AND action_id=:id")
                .bind("t", tenant)
                .bind("a", account)
                .bind("id", action)
                .map(
                        (r, m) ->
                                new Action(
                                        r.get("kind", String.class),
                                        r.get("intent_digest", String.class),
                                        decode(r.get("selection", String.class), Selection.class),
                                        r.get("request", String.class),
                                        r.get("receipt", String.class)))
                .one();
    }

    Mono<Fence> fence(String tenant, UUID account, UUID closure) {
        return db.sql(
                        "SELECT fence_id,closure_id,selection::text,state,version,acceptance::text"
                            + " FROM managed_payment_account_fence WHERE tenant_id=:t AND"
                            + " account_id=:a AND closure_id=:c")
                .bind("t", tenant)
                .bind("a", account)
                .bind("c", closure)
                .map(
                        (r, m) ->
                                new Fence(
                                        r.get("fence_id", UUID.class),
                                        r.get("closure_id", UUID.class),
                                        decode(r.get("selection", String.class), Selection.class),
                                        FenceState.valueOf(r.get("state", String.class)),
                                        r.get("version", Long.class),
                                        decode(
                                                r.get("acceptance", String.class),
                                                QuiescenceReceipt.class)))
                .one();
    }

    Mono<QuiescenceReceipt> quiesce(
            Selection selected, Quiesce input, long ledgerVersion, String digest) {
        return db.sql(
                        "SELECT"
                            + " public.managed_payment_account_quiesce(:t,:a,:action,:closure,:enrollment,:expected,CAST(:selection"
                            + " AS jsonb),:ledger,:digest)::text AS receipt")
                .bind("t", selected.tenantId())
                .bind("a", selected.accountId())
                .bind("action", input.actionId())
                .bind("closure", input.closureId())
                .bind("enrollment", input.enrollmentId())
                .bind("expected", input.expectedFenceVersion())
                .bind("selection", encode(selected))
                .bind("ledger", ledgerVersion)
                .bind("digest", digest)
                .map((r, m) -> decode(r.get("receipt", String.class), QuiescenceReceipt.class))
                .one()
                .switchIfEmpty(Mono.error(unavailable()));
    }

    Mono<ReleaseReceipt> release(String tenant, UUID account, Release input, String digest) {
        return db.sql(
                        "SELECT"
                            + " public.managed_payment_account_release(:t,:a,:action,:closure,:expected,:digest)::text"
                            + " AS receipt")
                .bind("t", tenant)
                .bind("a", account)
                .bind("action", input.actionId())
                .bind("closure", input.closureId())
                .bind("expected", input.expectedFenceVersion())
                .bind("digest", digest)
                .map((r, m) -> decode(r.get("receipt", String.class), ReleaseReceipt.class))
                .one()
                .switchIfEmpty(Mono.error(unavailable()));
    }

    Mono<ObservationReceipt> observation(String tenant, UUID account, UUID id) {
        return db.sql(
                        "SELECT o.receipt::text AS receipt FROM managed_payment_account_observation"
                            + " o JOIN managed_payment_account_fence f ON f.tenant_id=o.tenant_id"
                            + " AND f.account_id=o.account_id AND f.fence_id=o.fence_id WHERE"
                            + " o.tenant_id=:t AND o.account_id=:a AND o.observation_id=:id AND"
                            + " f.state='FENCED' AND f.version=o.fence_version")
                .bind("t", tenant)
                .bind("a", account)
                .bind("id", id)
                .map((r, m) -> decode(r.get("receipt", String.class), ObservationReceipt.class))
                .one();
    }

    Mono<ObservationReceipt> capture(String tenant, UUID account, UUID closure, UUID id) {
        return db.sql(
                        "SELECT public.managed_payment_account_capture(:t,:a,:c,:id)::text AS"
                            + " receipt")
                .bind("t", tenant)
                .bind("a", account)
                .bind("c", closure)
                .bind("id", id)
                .map((r, m) -> decode(r.get("receipt", String.class), ObservationReceipt.class))
                .one()
                .switchIfEmpty(Mono.error(unavailable()));
    }

    Mono<ObligationPage> page(ObservationReceipt receipt, long after, int limit) {
        return db.sql(
                        "SELECT"
                            + " obligation_id,ordinal,direction,state,effect_state,blocking,operation_version"
                            + " FROM managed_payment_account_observation_member WHERE tenant_id=:t"
                            + " AND account_id=:a AND observation_id=:id AND ordinal>:after ORDER"
                            + " BY ordinal LIMIT :n")
                .bind("t", receipt.selection().tenantId())
                .bind("a", receipt.selection().accountId())
                .bind("id", receipt.observationId())
                .bind("after", after)
                .bind("n", limit + 1)
                .map(
                        (r, m) ->
                                new Obligation(
                                        r.get("obligation_id", UUID.class),
                                        r.get("ordinal", Long.class),
                                        r.get("direction", String.class),
                                        r.get("state", String.class),
                                        r.get("effect_state", String.class),
                                        Boolean.TRUE.equals(r.get("blocking", Boolean.class)),
                                        r.get("operation_version", Long.class)))
                .all()
                .collectList()
                .map(
                        rows -> {
                            boolean more = rows.size() > limit;
                            var items = List.copyOf(rows.subList(0, Math.min(rows.size(), limit)));
                            return new ObligationPage(
                                    receipt, items, more ? items.getLast().ordinal() : null);
                        });
    }

    Mono<View> view(Fence fence) {
        return db.sql(
                        "SELECT receipt::text AS receipt FROM managed_payment_account_observation"
                            + " WHERE tenant_id=:t AND account_id=:a AND fence_id=:id ORDER BY"
                            + " (receipt->>'observedAt')::timestamptz DESC,observation_id DESC"
                            + " LIMIT 1")
                .bind("t", fence.selection().tenantId())
                .bind("a", fence.selection().accountId())
                .bind("id", fence.fenceId())
                .map((r, m) -> decode(r.get("receipt", String.class), ObservationReceipt.class))
                .one()
                .map(
                        o ->
                                new View(
                                        fence.fenceId(),
                                        fence.closureId(),
                                        fence.selection(),
                                        fence.state(),
                                        fence.version(),
                                        fence.state() == FenceState.FENCED
                                                        && fence.version() == o.fenceVersion()
                                                ? o.status()
                                                : ObservationStatus.UNKNOWN,
                                        o.observedSourceVersion(),
                                        o.observationId()))
                .defaultIfEmpty(
                        new View(
                                fence.fenceId(),
                                fence.closureId(),
                                fence.selection(),
                                fence.state(),
                                fence.version(),
                                ObservationStatus.UNKNOWN,
                                null,
                                null));
    }

    static ResponseStatusException unavailable() {
        return new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE, "RETAINED_PAYMENT_CLOSURE_EVIDENCE_UNAVAILABLE");
    }
}
