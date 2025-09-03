package com.firefly.core.banking.payments.core.services.core.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentFeeDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
/**
 * Service interface for managing payment fees in a manager context.
 */
public interface PaymentFeeManagerService {

    /**
     * Retrieves a paginated list of payment fees for a given payment order.
     */
    Mono<PaginationResponse<PaymentFeeDTO>> getAllPaymentFees(UUID paymentOrderId, FilterRequest<PaymentFeeDTO> filterRequest);

    /**
     * Creates a new payment fee for the specified payment order.
     */
    Mono<PaymentFeeDTO> createPaymentFee(UUID paymentOrderId, PaymentFeeDTO paymentFeeDTO);

    /**
     * Retrieves a specific payment fee by its unique identifier, within the context of a payment order.
     */
    Mono<PaymentFeeDTO> getPaymentFeeById(UUID paymentOrderId, UUID paymentFeeId);

    /**
     * Updates an existing payment fee by its unique identifier.
     */
    Mono<PaymentFeeDTO> updatePaymentFee(UUID paymentOrderId, UUID paymentFeeId, PaymentFeeDTO paymentFeeDTO);

    /**
     * Deletes a specific payment fee by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePaymentFee(UUID paymentOrderId, UUID paymentFeeId);
}