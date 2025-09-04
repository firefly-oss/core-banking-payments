/*
 * Copyright 2025 Firefly Software Solutions Inc
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


package com.firefly.core.banking.payments.core.services.instruction.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.instruction.v1.PaymentInstructionDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentInstructionManagerService {

    /**
     * Retrieves a paginated list of payment instructions for a given payment order.
     */
    Mono<PaginationResponse<PaymentInstructionDTO>> getAllPaymentInstructions(
            UUID paymentOrderId,
            FilterRequest<PaymentInstructionDTO> filterRequest
    );

    /**
     * Creates a new payment instruction for the specified payment order.
     */
    Mono<PaymentInstructionDTO> createPaymentInstruction(UUID paymentOrderId, PaymentInstructionDTO paymentInstructionDTO);

    /**
     * Retrieves a specific payment instruction by its unique identifier, within the context of a payment order.
     */
    Mono<PaymentInstructionDTO> getPaymentInstructionById(UUID paymentOrderId, UUID paymentInstructionId);

    /**
     * Updates an existing payment instruction by its unique identifier.
     */
    Mono<PaymentInstructionDTO> updatePaymentInstruction(UUID paymentOrderId, UUID paymentInstructionId, PaymentInstructionDTO paymentInstructionDTO);

    /**
     * Deletes a specific payment instruction by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePaymentInstruction(UUID paymentOrderId, UUID paymentInstructionId);
}