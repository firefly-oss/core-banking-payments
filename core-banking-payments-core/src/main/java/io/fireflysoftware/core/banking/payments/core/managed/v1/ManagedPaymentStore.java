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

import static io.fireflysoftware.core.banking.payments.core.managed.v1.ManagedPaymentPolicy.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.fireflysoftware.core.banking.payments.interfaces.dtos.managed.v1.ManagedPaymentContracts.*;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import reactor.core.publisher.*;

import java.util.*;

@Component
public final class ManagedPaymentStore {
    final DatabaseClient db;
    private final ObjectMapper json =
            new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(
                            com.fasterxml.jackson.databind.SerializationFeature
                                    .WRITE_DATES_AS_TIMESTAMPS);

    public ManagedPaymentStore(DatabaseClient db) {
        this.db = db;
    }

    String encode(Object value) {
        try {
            return json.writeValueAsString(value);
        } catch (Exception e) {
            throw new IllegalStateException("PAYMENT_INTERNAL_ENCODING_FAILED", e);
        }
    }

    View decode(String value) {
        try {
            return json.readValue(value, View.class);
        } catch (Exception e) {
            throw new IllegalStateException("PAYMENT_RETAINED_RECORD_INVALID", e);
        }
    }

    Mono<Void> gate(String tenant, UUID id) {
        return db.sql("SELECT pg_advisory_xact_lock(hashtextextended(:key,0))")
                .bind("key", "managed-payment:" + tenant + ":" + id)
                .fetch()
                .rowsUpdated()
                .then();
    }

    Mono<Void> accountGates(String tenant, UUID... accounts) {
        return Flux.fromIterable(
                        Arrays.stream(accounts)
                                .distinct()
                                .sorted(Comparator.comparing(UUID::toString))
                                .toList())
                .concatMap(
                        account ->
                                db.sql(
                                                "SELECT"
                                                    + " public.managed_payment_account_gate(:tenant,:account)")
                                        .bind("tenant", tenant)
                                        .bind("account", account)
                                        .fetch()
                                        .rowsUpdated())
                .then();
    }

    Mono<View> byAction(String tenant, UUID action) {
        return db.sql(
                        "SELECT o.payload::text AS payload FROM managed_payment_action a JOIN"
                            + " managed_payment_operation o ON o.tenant_id=a.tenant_id AND"
                            + " o.payment_id=a.payment_id WHERE a.tenant_id=:t AND a.action_id=:a"
                            + " AND a.kind='CREATE'")
                .bind("t", tenant)
                .bind("a", action)
                .map((r, m) -> decode(r.get("payload", String.class)))
                .one();
    }

    Mono<View> read(String tenant, UUID payment) {
        return db.sql(
                        "SELECT payload::text AS payload FROM managed_payment_operation WHERE"
                                + " tenant_id=:t AND payment_id=:id")
                .bind("t", tenant)
                .bind("id", payment)
                .map((r, m) -> decode(r.get("payload", String.class)))
                .one();
    }

    Mono<Long> now() {
        return db.sql("SELECT floor(extract(epoch FROM clock_timestamp())*1000)::bigint AS now")
                .map((r, m) -> r.get("now", Long.class))
                .one();
    }

    Mono<Void> create(View v, String digest, UUID effect) {
        return db.sql(
                        "INSERT INTO"
                            + " managed_payment_operation(tenant_id,payment_id,source_account_id,destination_account_id,create_action_id,request_digest,payload,state,version)"
                            + " VALUES(:t,:id,:s,:d,:action,:digest,CAST(:payload AS"
                            + " jsonb),'RECEIVED',0)")
                .bind("t", v.tenant())
                .bind("id", v.paymentId())
                .bind("s", v.sourceAccountId())
                .bind("d", v.destinationAccountId())
                .bind("action", v.createActionId())
                .bind("digest", digest)
                .bind("payload", encode(v))
                .fetch()
                .rowsUpdated()
                .then(
                        db.sql(
                                        "INSERT INTO"
                                            + " managed_payment_action(tenant_id,payment_id,source_account_id,destination_account_id,action_id,kind,intent_digest,actor_subject)"
                                            + " VALUES(:t,:id,:s,:d,:action,'CREATE',:digest,:actor)"
                                            + " ON CONFLICT(tenant_id,action_id) DO NOTHING"
                                            + " RETURNING action_id")
                                .bind("t", v.tenant())
                                .bind("id", v.paymentId())
                                .bind("s", v.sourceAccountId())
                                .bind("d", v.destinationAccountId())
                                .bind("action", v.createActionId())
                                .bind("digest", digest)
                                .bind("actor", v.initiator())
                                .map((r, m) -> r.get("action_id", UUID.class))
                                .one()
                                .switchIfEmpty(Mono.error(fail(409, "PAYMENT_ACTION_CONFLICT"))))
                .then(
                        db.sql(
                                        "INSERT INTO"
                                            + " managed_payment_effect(tenant_id,payment_id,source_account_id,destination_account_id,effect_id,participant_action_id,kind)"
                                            + " VALUES(:t,:id,:s,:d,:e,:p,'RESERVE')")
                                .bind("t", v.tenant())
                                .bind("id", v.paymentId())
                                .bind("s", v.sourceAccountId())
                                .bind("d", v.destinationAccountId())
                                .bind("e", effect)
                                .bind("p", v.paymentId())
                                .fetch()
                                .rowsUpdated())
                .then(
                        db.sql(
                                        "INSERT INTO"
                                            + " managed_payment_projection(tenant_id,payment_id,source_account_id,destination_account_id,payload)"
                                            + " VALUES(:t,:id,:s,:d,CAST(:payload AS jsonb))")
                                .bind("t", v.tenant())
                                .bind("id", v.paymentId())
                                .bind("s", v.sourceAccountId())
                                .bind("d", v.destinationAccountId())
                                .bind("payload", encode(v))
                                .fetch()
                                .rowsUpdated())
                .then(event(v, "CREATED", v.initiator(), v.createActionId(), effect));
    }

    Mono<Void> event(View v, String type, String actor, UUID action, UUID effect) {
        String payload =
                encode(
                        new HistoryEvent(
                                v.version(),
                                type,
                                actor,
                                java.time.Instant.now(),
                                action,
                                effect,
                                v.state(),
                                v.postingId(),
                                v.reversalId()));
        return db.sql(
                        "INSERT INTO"
                            + " managed_payment_history(tenant_id,payment_id,source_account_id,destination_account_id,version,payload)"
                            + " VALUES(:t,:id,:s,:d,:v,CAST(:payload AS jsonb))")
                .bind("t", v.tenant())
                .bind("id", v.paymentId())
                .bind("s", v.sourceAccountId())
                .bind("d", v.destinationAccountId())
                .bind("v", v.version())
                .bind("payload", payload)
                .fetch()
                .rowsUpdated()
                .then(
                        db.sql(
                                        "INSERT INTO"
                                            + " managed_payment_outbox(tenant_id,payment_id,source_account_id,destination_account_id,version,event_id,payload)"
                                            + " VALUES(:t,:id,:s,:d,:v,:event,CAST(:payload AS"
                                            + " jsonb))")
                                .bind("t", v.tenant())
                                .bind("id", v.paymentId())
                                .bind("s", v.sourceAccountId())
                                .bind("d", v.destinationAccountId())
                                .bind("v", v.version())
                                .bind("event", UUID.randomUUID())
                                .bind("payload", payload)
                                .fetch()
                                .rowsUpdated())
                .then();
    }

    Flux<HistoryEvent> history(String tenant, UUID id, long after, int limit) {
        return db.sql(
                        "SELECT payload::text AS payload FROM managed_payment_history WHERE"
                                + " tenant_id=:t AND payment_id=:id AND version>:v ORDER BY version"
                                + " LIMIT :n")
                .bind("t", tenant)
                .bind("id", id)
                .bind("v", after)
                .bind("n", limit)
                .map(
                        (r, m) -> {
                            try {
                                return json.readValue(
                                        r.get("payload", String.class), HistoryEvent.class);
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        })
                .all();
    }

    record RetainedAction(UUID payment, String kind, String digest, String actor, String receipt) {}

    record EffectRow(
            UUID id,
            UUID participant,
            EffectKind kind,
            String state,
            long fence,
            int attempts,
            String owner,
            java.time.Instant lease) {}

    Mono<RetainedAction> action(String tenant, UUID action) {
        return db.sql(
                        "SELECT payment_id,kind,intent_digest,actor_subject,receipt::text AS"
                                + " receipt FROM managed_payment_action WHERE tenant_id=:t AND"
                                + " action_id=:a")
                .bind("t", tenant)
                .bind("a", action)
                .map(
                        (r, m) ->
                                new RetainedAction(
                                        r.get("payment_id", UUID.class),
                                        r.get("kind", String.class),
                                        r.get("intent_digest", String.class),
                                        r.get("actor_subject", String.class),
                                        r.get("receipt", String.class)))
                .one();
    }

    <T> T decode(String value, Class<T> type) {
        try {
            return json.readValue(value, type);
        } catch (Exception e) {
            throw new IllegalStateException("PAYMENT_RETAINED_RECEIPT_INVALID", e);
        }
    }

    Mono<Void> insertAction(
            View v, UUID action, String kind, String digest, String actor, Object receipt) {
        var q =
                db.sql(
                                "INSERT INTO"
                                    + " managed_payment_action(tenant_id,payment_id,source_account_id,destination_account_id,action_id,kind,intent_digest,actor_subject,receipt)"
                                    + " VALUES(:t,:id,:s,:d,:a,:kind,:digest,:actor,CAST(:receipt"
                                    + " AS jsonb)) ON CONFLICT(tenant_id,action_id) DO NOTHING"
                                    + " RETURNING action_id")
                        .bind("t", v.tenant())
                        .bind("id", v.paymentId())
                        .bind("s", v.sourceAccountId())
                        .bind("d", v.destinationAccountId())
                        .bind("a", action)
                        .bind("kind", kind)
                        .bind("digest", digest)
                        .bind("actor", actor);
        q =
                receipt == null
                        ? q.bindNull("receipt", String.class)
                        : q.bind("receipt", encode(receipt));
        return q.map((r, m) -> r.get("action_id", UUID.class))
                .one()
                .switchIfEmpty(Mono.error(fail(409, "PAYMENT_ACTION_CONFLICT")))
                .then();
    }

    Mono<EffectRow> effect(String tenant, UUID id) {
        return db.sql("SELECT * FROM managed_payment_effect WHERE tenant_id=:t AND effect_id=:id")
                .bind("t", tenant)
                .bind("id", id)
                .map(
                        (r, m) -> {
                            var lease = r.get("lease_until", java.time.OffsetDateTime.class);
                            return new EffectRow(
                                    r.get("effect_id", UUID.class),
                                    r.get("participant_action_id", UUID.class),
                                    EffectKind.valueOf(r.get("kind", String.class)),
                                    r.get("state", String.class),
                                    r.get("fence", Long.class),
                                    r.get("attempts", Integer.class),
                                    r.get("owner_subject", String.class),
                                    lease == null ? null : lease.toInstant());
                        })
                .one()
                .switchIfEmpty(Mono.error(fail(409, "RETAINED_PAYMENT_EFFECT_REQUIRED")));
    }

    Mono<Void> claimEffect(String tenant, EffectRow effect, String actor, java.time.Instant until) {
        return db.sql(
                        "UPDATE managed_payment_effect SET"
                            + " state='CLAIMED',fence=fence+1,attempts=attempts+1,owner_subject=:actor,lease_until=:until"
                            + " WHERE tenant_id=:t AND effect_id=:id AND fence=:f AND"
                            + " fence<9223372036854775807 AND attempts<2147483647")
                .bind("t", tenant)
                .bind("id", effect.id())
                .bind("f", effect.fence())
                .bind("actor", actor)
                .bind("until", until.atOffset(java.time.ZoneOffset.UTC))
                .fetch()
                .rowsUpdated()
                .filter(n -> n == 1)
                .switchIfEmpty(Mono.error(fail(409, "PAYMENT_EFFECT_FENCE_CONFLICT")))
                .then();
    }

    Mono<Void> save(View v, String type, String actor, UUID action, UUID effect) {
        return db.sql(
                        "UPDATE managed_payment_operation SET payload=CAST(:payload AS"
                                + " jsonb),state=:state,version=:version WHERE tenant_id=:t AND"
                                + " payment_id=:id AND version=:prior")
                .bind("payload", encode(v))
                .bind("state", v.state().name())
                .bind("version", v.version())
                .bind("t", v.tenant())
                .bind("id", v.paymentId())
                .bind("prior", v.version() - 1)
                .fetch()
                .rowsUpdated()
                .filter(n -> n == 1)
                .switchIfEmpty(Mono.error(fail(409, "PAYMENT_VERSION_CONFLICT")))
                .then(
                        db.sql(
                                        "UPDATE managed_payment_projection SET"
                                            + " payload=CAST(:payload AS jsonb) WHERE tenant_id=:t"
                                            + " AND payment_id=:id")
                                .bind("payload", encode(v))
                                .bind("t", v.tenant())
                                .bind("id", v.paymentId())
                                .fetch()
                                .rowsUpdated()
                                .filter(n -> n == 1)
                                .switchIfEmpty(
                                        Mono.error(fail(409, "PAYMENT_PROJECTION_REQUIRED"))))
                .then(event(v, type, actor, action, effect));
    }

    static View evolve(View v, State state, UUID pending, String code) {
        if (v.version() == Long.MAX_VALUE) throw fail(409, "PAYMENT_VERSION_EXHAUSTED");
        return new View(
                v.paymentId(),
                v.createActionId(),
                v.tenant(),
                v.sourceAccountId(),
                v.destinationAccountId(),
                v.amount(),
                v.currency(),
                v.currencyScale(),
                v.currencyPolicy(),
                v.ttlSeconds(),
                v.customerNotAfterEpochMillis(),
                v.notAfterEpochMillis(),
                v.initiator(),
                state,
                v.version() + 1,
                v.reservationId(),
                v.postingId(),
                v.reversalId(),
                v.absenceReceiptId(),
                v.ledgerVersion(),
                v.observedAt(),
                pending,
                code,
                v.ledgerState(),
                v.reservationExpiresAt());
    }

    Mono<Void> deferEffect(String tenant, EffectRow effect) {
        int delay = Math.min(30, 1 << Math.min(5, Math.max(0, effect.attempts() - 1)));
        return db.sql(
                        "UPDATE managed_payment_effect SET"
                            + " state=:state,owner_subject=NULL,lease_until=NULL,next_attempt_at=clock_timestamp()+:delay"
                            + " * interval '1 second' WHERE tenant_id=:t AND effect_id=:id AND"
                            + " fence=:f")
                .bind("state", effect.attempts() >= 10 ? "EXHAUSTED" : "PENDING")
                .bind("delay", delay)
                .bind("t", tenant)
                .bind("id", effect.id())
                .bind("f", effect.fence())
                .fetch()
                .rowsUpdated()
                .filter(n -> n == 1)
                .switchIfEmpty(Mono.error(fail(409, "PAYMENT_EFFECT_FENCE_CONFLICT")))
                .then();
    }

    Flux<View> queue(String tenant, UUID after, int limit) {
        var q =
                db.sql(
                                "SELECT o.payload::text AS payload FROM managed_payment_operation o"
                                    + " LEFT JOIN managed_payment_effect e ON"
                                    + " e.tenant_id=o.tenant_id AND"
                                    + " e.effect_id=(o.payload->>'pendingEffectId')::uuid WHERE"
                                    + " o.tenant_id=:t AND (CAST(:after AS uuid) IS NULL OR"
                                    + " o.payment_id>CAST(:after AS uuid)) AND ((e.state"
                                    + " IN('PENDING','CLAIMED') AND"
                                    + " e.next_attempt_at<=clock_timestamp() AND (e.lease_until IS"
                                    + " NULL OR e.lease_until<=clock_timestamp())) OR"
                                    + " (o.state='RESERVED' AND o.payload->>'pendingEffectId' IS"
                                    + " NULL AND"
                                    + " coalesce((o.payload->>'reservationExpiresAt')::timestamptz,to_timestamp((o.payload->>'notAfterEpochMillis')::numeric/1000))<=clock_timestamp()))"
                                    + " ORDER BY o.payment_id LIMIT :n")
                        .bind("t", tenant)
                        .bind("n", limit);
        q = after == null ? q.bindNull("after", UUID.class) : q.bind("after", after);
        return q.map((r, m) -> decode(r.get("payload", String.class))).all();
    }

    Mono<Void> acknowledgeEffect(String tenant, EffectRow effect) {
        return db.sql(
                        "UPDATE managed_payment_effect SET"
                            + " state='ACKNOWLEDGED',owner_subject=NULL,lease_until=NULL WHERE"
                            + " tenant_id=:t AND effect_id=:id AND fence=:f AND state='CLAIMED'")
                .bind("t", tenant)
                .bind("id", effect.id())
                .bind("f", effect.fence())
                .fetch()
                .rowsUpdated()
                .filter(n -> n == 1)
                .switchIfEmpty(Mono.error(fail(409, "PAYMENT_EFFECT_FENCE_CONFLICT")))
                .then();
    }

    Mono<EffectRow> insertEffect(View v, EffectKind kind) {
        UUID id = UUID.randomUUID(), participant = UUID.randomUUID();
        return db.sql(
                        "INSERT INTO"
                            + " managed_payment_effect(tenant_id,payment_id,source_account_id,destination_account_id,effect_id,participant_action_id,kind)"
                            + " VALUES(:t,:p,:s,:d,:id,:participant,:kind)")
                .bind("t", v.tenant())
                .bind("p", v.paymentId())
                .bind("s", v.sourceAccountId())
                .bind("d", v.destinationAccountId())
                .bind("id", id)
                .bind("participant", participant)
                .bind("kind", kind.name())
                .fetch()
                .rowsUpdated()
                .thenReturn(new EffectRow(id, participant, kind, "PENDING", 0, 0, null, null));
    }

    Mono<EffectRow> observationEffect(View v) {
        return db.sql(
                        "SELECT effect_id FROM managed_payment_effect WHERE tenant_id=:t AND"
                                + " payment_id=:p AND kind='OBSERVE'")
                .bind("t", v.tenant())
                .bind("p", v.paymentId())
                .map((r, m) -> r.get("effect_id", UUID.class))
                .one()
                .flatMap(id -> effect(v.tenant(), id))
                .switchIfEmpty(Mono.defer(() -> insertEffect(v, EffectKind.OBSERVE)));
    }

    Mono<Void> resetEffect(String tenant, EffectRow e) {
        return db.sql(
                        "UPDATE managed_payment_effect SET"
                            + " state='PENDING',owner_subject=NULL,lease_until=NULL,next_attempt_at=clock_timestamp()"
                            + " WHERE tenant_id=:t AND effect_id=:id AND fence=:f")
                .bind("t", tenant)
                .bind("id", e.id())
                .bind("f", e.fence())
                .fetch()
                .rowsUpdated()
                .filter(n -> n == 1)
                .switchIfEmpty(Mono.error(fail(409, "PAYMENT_EFFECT_FENCE_CONFLICT")))
                .then();
    }
}
