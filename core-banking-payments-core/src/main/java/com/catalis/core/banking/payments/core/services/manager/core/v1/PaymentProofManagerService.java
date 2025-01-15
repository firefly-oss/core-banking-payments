package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentProofDTO;
import reactor.core.publisher.Mono;

public interface PaymentProofManagerService {

    /**
     * Retrieves a paginated list of payment proofs for a given payment order.
     */
    Mono<PaginationResponse<PaymentProofDTO>> getAllPaymentProofs(Long paymentOrderId, FilterRequest<PaymentProofDTO> filterRequest);

    /**
     * Creates a new payment proof record for the specified payment order.
     */
    Mono<PaymentProofDTO> createPaymentProof(Long paymentOrderId, PaymentProofDTO paymentProofDTO);

    /**
     * Retrieves a specific payment proof by its unique identifier, within the context of a payment order.
     */
    Mono<PaymentProofDTO> getPaymentProofById(Long paymentOrderId, Long paymentProofId);

    /**
     * Updates an existing payment proof by its unique identifier.
     */
    Mono<PaymentProofDTO> updatePaymentProof(Long paymentOrderId, Long paymentProofId, PaymentProofDTO paymentProofDTO);

    /**
     * Deletes a specific payment proof by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePaymentProof(Long paymentOrderId, Long paymentProofId);
}