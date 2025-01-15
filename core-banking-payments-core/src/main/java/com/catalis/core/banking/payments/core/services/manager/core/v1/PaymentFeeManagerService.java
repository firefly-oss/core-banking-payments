package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentFeeDTO;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing payment fees in a manager context.
 */
public interface PaymentFeeManagerService {

    /**
     * Retrieves a paginated list of payment fees for a given payment order.
     */
    Mono<PaginationResponse<PaymentFeeDTO>> getAllPaymentFees(Long paymentOrderId, FilterRequest<PaymentFeeDTO> filterRequest);

    /**
     * Creates a new payment fee for the specified payment order.
     */
    Mono<PaymentFeeDTO> createPaymentFee(Long paymentOrderId, PaymentFeeDTO paymentFeeDTO);

    /**
     * Retrieves a specific payment fee by its unique identifier, within the context of a payment order.
     */
    Mono<PaymentFeeDTO> getPaymentFeeById(Long paymentOrderId, Long paymentFeeId);

    /**
     * Updates an existing payment fee by its unique identifier.
     */
    Mono<PaymentFeeDTO> updatePaymentFee(Long paymentOrderId, Long paymentFeeId, PaymentFeeDTO paymentFeeDTO);

    /**
     * Deletes a specific payment fee by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePaymentFee(Long paymentOrderId, Long paymentFeeId);
}