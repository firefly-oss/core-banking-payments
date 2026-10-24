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

import io.fireflysoftware.core.banking.payments.core.managed.v1.LedgerFundsObservationPort.*;
import io.fireflysoftware.core.banking.payments.interfaces.dtos.managed.v1.ManagedPaymentContracts.*;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ManagedPaymentObservationService {
    private final ManagedPaymentService service;
    private final ManagedPaymentCoordinator coordinator;
    private final ManagedPaymentStore store;
    private final LedgerFundsObservationPort ledger;

    public ManagedPaymentObservationService(
            ManagedPaymentService service,
            ManagedPaymentCoordinator coordinator,
            LedgerFundsObservationPort ledger) {
        this.service = service;
        this.coordinator = coordinator;
        this.store = service.store;
        this.ledger = ledger;
    }

    record Prepared(View payment, ManagedPaymentStore.EffectRow effect) {}

    record Observation(VerifiedFundsObservation receipt, FailureCode failure) {}

    public Mono<View> acknowledge(String tenant, UUID payment, Acknowledge input) {
        return current()
                .flatMap(
                        call -> {
                            require(call, tenant, "payments:coordinate");
                            require(call, tenant, "funds:read");
                            require(call, tenant, "funds:reconcile");
                            if (input == null
                                    || input.effectId() == null
                                    || input.fence() == null
                                    || input.fence() < 1
                                    || input.expectedVersion() == null
                                    || input.expectedVersion() < 0)
                                return Mono.error(
                                        fail(400, "EXACT_PAYMENT_ACKNOWLEDGMENT_REQUIRED"));
                            return service.transaction(
                                            call,
                                            tenant,
                                            "READ",
                                            "payments:coordinate",
                                            accounts(call),
                                            () ->
                                                    store.gate(tenant, payment)
                                                            .then(
                                                                    store.read(tenant, payment)
                                                                            .switchIfEmpty(
                                                                                    Mono.error(
                                                                                            fail(
                                                                                                    404,
                                                                                                    "MANAGED_PAYMENT_NOT_FOUND"))))
                                                            .flatMap(
                                                                    v ->
                                                                            store.effect(
                                                                                            tenant,
                                                                                            input
                                                                                                    .effectId())
                                                                                    .flatMap(
                                                                                            e ->
                                                                                                    store.now()
                                                                                                            .map(
                                                                                                                    now -> {
                                                                                                                        ManagedPaymentCoordinator
                                                                                                                                .effectAuthority(
                                                                                                                                        call,
                                                                                                                                        tenant,
                                                                                                                                        e,
                                                                                                                                        v);
                                                                                                                        require(
                                                                                                                                call,
                                                                                                                                tenant,
                                                                                                                                "funds:read",
                                                                                                                                v
                                                                                                                                        .sourceAccountId(),
                                                                                                                                v
                                                                                                                                        .destinationAccountId());
                                                                                                                        require(
                                                                                                                                call,
                                                                                                                                tenant,
                                                                                                                                "funds:reconcile",
                                                                                                                                v
                                                                                                                                        .sourceAccountId(),
                                                                                                                                v
                                                                                                                                        .destinationAccountId());
                                                                                                                        ManagedPaymentCoordinator
                                                                                                                                .owned(
                                                                                                                                        e,
                                                                                                                                        v,
                                                                                                                                        call,
                                                                                                                                        input
                                                                                                                                                .fence(),
                                                                                                                                        input
                                                                                                                                                .expectedVersion(),
                                                                                                                                        now);
                                                                                                                        return new Prepared(
                                                                                                                                v,
                                                                                                                                e);
                                                                                                                    }))))
                                    .flatMap(
                                            prepared ->
                                                    ledger.observe(tenant, prepared.payment())
                                                            .map(
                                                                    observed ->
                                                                            new Observation(
                                                                                    observed, null))
                                                            .onErrorResume(
                                                                    ObservationUnavailable.class,
                                                                    e ->
                                                                            Mono.just(
                                                                                    new Observation(
                                                                                            null,
                                                                                            e
                                                                                                    .code())))
                                                            .flatMap(
                                                                    result -> {
                                                                        if (result.failure()
                                                                                != null)
                                                                            return coordinator
                                                                                    .recordFailure(
                                                                                            tenant,
                                                                                            payment,
                                                                                            new Failure(
                                                                                                    input
                                                                                                            .effectId(),
                                                                                                    input
                                                                                                            .fence(),
                                                                                                    input
                                                                                                            .expectedVersion(),
                                                                                                    result
                                                                                                            .failure()));
                                                                        var found =
                                                                                result.receipt();
                                                                        if ("HELD"
                                                                                        .equals(
                                                                                                found
                                                                                                        .ledgerState())
                                                                                && (prepared.effect()
                                                                                                        .kind()
                                                                                                == EffectKind
                                                                                                        .COMMIT
                                                                                        || prepared.effect()
                                                                                                        .kind()
                                                                                                == EffectKind
                                                                                                        .RELEASE))
                                                                            return coordinator
                                                                                    .recordFailure(
                                                                                            tenant,
                                                                                            payment,
                                                                                            new Failure(
                                                                                                    input
                                                                                                            .effectId(),
                                                                                                    input
                                                                                                            .fence(),
                                                                                                    input
                                                                                                            .expectedVersion(),
                                                                                                    FailureCode
                                                                                                            .OBSERVATION_UNAVAILABLE));
                                                                        return service.transaction(
                                                                                call,
                                                                                tenant,
                                                                                "OBSERVE",
                                                                                "payments:coordinate",
                                                                                accounts(call),
                                                                                () ->
                                                                                        store.gate(
                                                                                                        tenant,
                                                                                                        payment)
                                                                                                .then(
                                                                                                        store.read(
                                                                                                                        tenant,
                                                                                                                        payment)
                                                                                                                .switchIfEmpty(
                                                                                                                        Mono
                                                                                                                                .error(
                                                                                                                                        fail(
                                                                                                                                                404,
                                                                                                                                                "MANAGED_PAYMENT_NOT_FOUND"))))
                                                                                                .flatMap(
                                                                                                        v ->
                                                                                                                store.effect(
                                                                                                                                tenant,
                                                                                                                                input
                                                                                                                                        .effectId())
                                                                                                                        .flatMap(
                                                                                                                                e ->
                                                                                                                                        store.now()
                                                                                                                                                .flatMap(
                                                                                                                                                        now -> {
                                                                                                                                                            ManagedPaymentCoordinator
                                                                                                                                                                    .effectAuthority(
                                                                                                                                                                            call,
                                                                                                                                                                            tenant,
                                                                                                                                                                            e,
                                                                                                                                                                            v);
                                                                                                                                                            require(
                                                                                                                                                                    call,
                                                                                                                                                                    tenant,
                                                                                                                                                                    "funds:read",
                                                                                                                                                                    v
                                                                                                                                                                            .sourceAccountId(),
                                                                                                                                                                    v
                                                                                                                                                                            .destinationAccountId());
                                                                                                                                                            require(
                                                                                                                                                                    call,
                                                                                                                                                                    tenant,
                                                                                                                                                                    "funds:reconcile",
                                                                                                                                                                    v
                                                                                                                                                                            .sourceAccountId(),
                                                                                                                                                                    v
                                                                                                                                                                            .destinationAccountId());
                                                                                                                                                            ManagedPaymentCoordinator
                                                                                                                                                                    .owned(
                                                                                                                                                                            e,
                                                                                                                                                                            v,
                                                                                                                                                                            call,
                                                                                                                                                                            input
                                                                                                                                                                                    .fence(),
                                                                                                                                                                            input
                                                                                                                                                                                    .expectedVersion(),
                                                                                                                                                                            now);
                                                                                                                                                            State
                                                                                                                                                                    state =
                                                                                                                                                                            switch (found
                                                                                                                                                                                    .ledgerState()) {
                                                                                                                                                                                case "HELD" ->
                                                                                                                                                                                        State
                                                                                                                                                                                                .RESERVED;
                                                                                                                                                                                case "COMMITTED" ->
                                                                                                                                                                                        State
                                                                                                                                                                                                .COMPLETED;
                                                                                                                                                                                case "REVERSED" ->
                                                                                                                                                                                        State
                                                                                                                                                                                                .REVERSED;
                                                                                                                                                                                case "RELEASED" ->
                                                                                                                                                                                        State
                                                                                                                                                                                                .CANCELLED;
                                                                                                                                                                                case "EXPIRED",
                                                                                                                                                                                        "ABSENT_CLOSED" ->
                                                                                                                                                                                        State
                                                                                                                                                                                                .EXPIRED;
                                                                                                                                                                                default ->
                                                                                                                                                                                        throw fail(
                                                                                                                                                                                                503,
                                                                                                                                                                                                "VERIFIED_LEDGER_STATE_REQUIRED");
                                                                                                                                                                            };
                                                                                                                                                            if (v
                                                                                                                                                                            .version()
                                                                                                                                                                    == Long
                                                                                                                                                                            .MAX_VALUE)
                                                                                                                                                                return Mono
                                                                                                                                                                        .error(
                                                                                                                                                                                fail(
                                                                                                                                                                                        409,
                                                                                                                                                                                        "PAYMENT_VERSION_EXHAUSTED"));
                                                                                                                                                            View
                                                                                                                                                                    next =
                                                                                                                                                                            new View(
                                                                                                                                                                                    v
                                                                                                                                                                                            .paymentId(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .createActionId(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .tenant(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .sourceAccountId(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .destinationAccountId(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .amount(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .currency(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .currencyScale(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .currencyPolicy(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .ttlSeconds(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .customerNotAfterEpochMillis(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .notAfterEpochMillis(),
                                                                                                                                                                                    v
                                                                                                                                                                                            .initiator(),
                                                                                                                                                                                    state,
                                                                                                                                                                                    v
                                                                                                                                                                                                    .version()
                                                                                                                                                                                            + 1,
                                                                                                                                                                                    found
                                                                                                                                                                                            .reservationId(),
                                                                                                                                                                                    found
                                                                                                                                                                                            .postingId(),
                                                                                                                                                                                    found
                                                                                                                                                                                            .reversalId(),
                                                                                                                                                                                    found
                                                                                                                                                                                            .absenceReceiptId(),
                                                                                                                                                                                    found
                                                                                                                                                                                            .ledgerVersion(),
                                                                                                                                                                                    found
                                                                                                                                                                                            .observedAt(),
                                                                                                                                                                                    null,
                                                                                                                                                                                    null,
                                                                                                                                                                                    found
                                                                                                                                                                                            .ledgerState(),
                                                                                                                                                                                    found
                                                                                                                                                                                            .expiresAt());
                                                                                                                                                            return store.acknowledgeEffect(
                                                                                                                                                                            tenant,
                                                                                                                                                                            e)
                                                                                                                                                                    .then(
                                                                                                                                                                            store
                                                                                                                                                                                    .save(
                                                                                                                                                                                            next,
                                                                                                                                                                                            "LEDGER_"
                                                                                                                                                                                                    + found
                                                                                                                                                                                                            .ledgerState(),
                                                                                                                                                                                            call.principal()
                                                                                                                                                                                                    .subject(),
                                                                                                                                                                                            null,
                                                                                                                                                                                            e
                                                                                                                                                                                                    .id()))
                                                                                                                                                                    .thenReturn(
                                                                                                                                                                            next);
                                                                                                                                                        }))));
                                                                    }));
                        });
    }
}
