package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentOrderDTO;
import reactor.core.publisher.Mono;

public interface PaymentOrderManagerService {

    /**
     * Retrieves a paginated list of payment orders based on various criteria.
     */
    Mono<PaginationResponse<PaymentOrderDTO>> getAllPaymentOrders(FilterRequest<PaymentOrderDTO> filterRequest);

    /**
     * Creates a new payment order.
     */
    Mono<PaymentOrderDTO> createPaymentOrder(PaymentOrderDTO paymentOrderDTO);

    /**
     * Retrieves a specific payment order by its unique identifier.
     */
    Mono<PaymentOrderDTO> getPaymentOrderById(Long paymentOrderId);

    /**
     * Updates an existing payment order by its unique identifier.
     */
    Mono<PaymentOrderDTO> updatePaymentOrder(Long paymentOrderId, PaymentOrderDTO paymentOrderDTO);

    /**
     * Deletes a specific payment order by its unique identifier.
     */
    Mono<Void> deletePaymentOrder(Long paymentOrderId);
}
