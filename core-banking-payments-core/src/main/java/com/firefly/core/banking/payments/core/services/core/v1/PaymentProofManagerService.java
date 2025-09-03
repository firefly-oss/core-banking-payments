package com.firefly.core.banking.payments.core.services.core.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentProofDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentProofManagerService {

    /**
     * Retrieves a paginated list of payment proofs for a given payment order.
     */
    Mono<PaginationResponse<PaymentProofDTO>> getAllPaymentProofs(UUID paymentOrderId, FilterRequest<PaymentProofDTO> filterRequest);

    /**
     * Creates a new payment proof record for the specified payment order.
     */
    Mono<PaymentProofDTO> createPaymentProof(UUID paymentOrderId, PaymentProofDTO paymentProofDTO);

    /**
     * Retrieves a specific payment proof by its unique identifier, within the context of a payment order.
     */
    Mono<PaymentProofDTO> getPaymentProofById(UUID paymentOrderId, UUID paymentProofId);

    /**
     * Updates an existing payment proof by its unique identifier.
     */
    Mono<PaymentProofDTO> updatePaymentProof(UUID paymentOrderId, UUID paymentProofId, PaymentProofDTO paymentProofDTO);

    /**
     * Deletes a specific payment proof by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePaymentProof(UUID paymentOrderId, UUID paymentProofId);
}