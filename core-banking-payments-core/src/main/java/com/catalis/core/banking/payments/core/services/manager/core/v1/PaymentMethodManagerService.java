package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentMethodDTO;
import reactor.core.publisher.Mono;

public interface PaymentMethodManagerService {

    /**
     * Retrieves a paginated list of payment methods based on various filtering criteria.
     */
    Mono<PaginationResponse<PaymentMethodDTO>> getAllPaymentMethods(FilterRequest<PaymentMethodDTO> filterRequest);

    /**
     * Creates a new payment method.
     */
    Mono<PaymentMethodDTO> createPaymentMethod(PaymentMethodDTO paymentMethodDTO);

    /**
     * Retrieves a specific payment method by its unique identifier.
     */
    Mono<PaymentMethodDTO> getPaymentMethodById(Long paymentMethodId);

    /**
     * Updates an existing payment method by its unique identifier.
     */
    Mono<PaymentMethodDTO> updatePaymentMethod(Long paymentMethodId, PaymentMethodDTO paymentMethodDTO);

    /**
     * Deletes a specific payment method by its unique identifier.
     */
    Mono<Void> deletePaymentMethod(Long paymentMethodId);
}