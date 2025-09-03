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