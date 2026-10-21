/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.banking.payments.core.services.debit.v1;

import io.fireflysoftware.core.banking.payments.core.instructions.PaymentInstructions;
import io.fireflysoftware.core.banking.payments.interfaces.dtos.debit.v1.DirectDebitCollectionDTO;
import io.fireflysoftware.core.banking.payments.interfaces.dtos.debit.v1.StandingOrderRunDTO;
import io.fireflysoftware.core.banking.payments.models.entities.debit.v1.DirectDebitCollection;
import io.fireflysoftware.core.banking.payments.models.entities.debit.v1.StandingOrderRun;
import io.fireflysoftware.core.banking.payments.models.repositories.debit.v1.DirectDebitCollectionRepository;
import io.fireflysoftware.core.banking.payments.models.repositories.debit.v1.DirectDebitMandateRepository;
import io.fireflysoftware.core.banking.payments.models.repositories.debit.v1.StandingOrderRunRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class DirectDebitCollectionService {

    private final DirectDebitMandateRepository mandates;
    private final DirectDebitCollectionRepository collections;
    private final StandingOrderRunRepository runs;

    public DirectDebitCollectionService(DirectDebitMandateRepository mandates,
                                        DirectDebitCollectionRepository collections,
                                        StandingOrderRunRepository runs) {
        this.mandates = mandates;
        this.collections = collections;
        this.runs = runs;
    }

    public Mono<DirectDebitCollectionDTO> collect(DirectDebitCollectionDTO request) {
        if (request == null || request.directDebitMandateId() == null || request.valueDate() == null) {
            return Mono.error(new IllegalArgumentException("Mandate and value date are required"));
        }
        return mandates.findById(request.directDebitMandateId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Direct debit mandate was not found")))
                .flatMap(mandate -> {
                    try {
                        PaymentInstructions.requireCollectable(new PaymentInstructions.DirectDebit(
                                PaymentInstructions.MandateStatus.valueOf(mandate.getStatus().name()),
                                mandate.getAmountLimit(), request.currency(), request.amount()));
                    } catch (IllegalArgumentException exception) {
                        return Mono.error(exception);
                    }
                    DirectDebitCollection row = new DirectDebitCollection();
                    row.setDirectDebitCollectionId(UUID.randomUUID());
                    row.setDirectDebitMandateId(mandate.getDirectDebitMandateId());
                    row.setAmount(request.amount());
                    row.setCurrency(request.currency());
                    row.setValueDate(request.valueDate());
                    row.setStatus("CONFIRMED");
                    return collections.save(row).map(this::toCollection);
                });
    }

    public Mono<DirectDebitCollectionDTO> getCollection(UUID collectionId) {
        return collections.findById(collectionId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Direct debit collection was not found")))
                .map(this::toCollection);
    }

    public Mono<StandingOrderRunDTO> runStandingOrder(StandingOrderRunDTO request) {
        if (request == null || request.paymentScheduleId() == null || request.valueDate() == null) {
            return Mono.error(new IllegalArgumentException("Schedule and value date are required"));
        }
        PaymentInstructions.StandingOrder next;
        try {
            next = PaymentInstructions.executeStandingOrder(new PaymentInstructions.StandingOrder(
                    request.nextExecutionDate(), null, request.executionCount() == null ? 0 : request.executionCount(),
                    null, request.amount(), request.currency()), request.valueDate());
        } catch (IllegalArgumentException exception) {
            return Mono.error(exception);
        }
        StandingOrderRun row = new StandingOrderRun();
        row.setStandingOrderRunId(UUID.randomUUID());
        row.setPaymentScheduleId(request.paymentScheduleId());
        row.setAmount(next.amount());
        row.setCurrency(next.currency());
        row.setValueDate(request.valueDate());
        row.setNextExecutionDate(next.nextExecution());
        row.setExecutionCount(next.executions());
        return runs.save(row).map(this::toRun);
    }

    public Mono<StandingOrderRunDTO> getRun(UUID runId) {
        return runs.findById(runId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Standing order run was not found")))
                .map(this::toRun);
    }

    private DirectDebitCollectionDTO toCollection(DirectDebitCollection row) {
        return new DirectDebitCollectionDTO(row.getDirectDebitCollectionId(), row.getDirectDebitMandateId(),
                row.getAmount(), row.getCurrency(), row.getValueDate(), row.getStatus());
    }

    private StandingOrderRunDTO toRun(StandingOrderRun row) {
        return new StandingOrderRunDTO(row.getStandingOrderRunId(), row.getPaymentScheduleId(), row.getAmount(),
                row.getCurrency(), row.getValueDate(), row.getNextExecutionDate(), row.getExecutionCount());
    }
}
