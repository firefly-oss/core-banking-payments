package com.catalis.core.banking.payments.core.services.manager.provider.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentProviderDTO;
import reactor.core.publisher.Mono;

public interface PaymentProviderManagerService {

    /**
     * Retrieves a paginated list of payment providers for a given payment order.
     */
    Mono<PaginationResponse<PaymentProviderDTO>> getAllPaymentProviders(Long paymentOrderId,
                                                                        FilterRequest<PaymentProviderDTO> filterRequest);

    /**
     * Creates a new payment provider for the specified payment order.
     */
    Mono<PaymentProviderDTO> createPaymentProvider(Long paymentOrderId, PaymentProviderDTO paymentProviderDTO);

    /**
     * Retrieves a specific payment provider by its unique identifier, within the context of a payment order.
     */
    Mono<PaymentProviderDTO> getPaymentProviderById(Long paymentOrderId, Long paymentProviderId);

    /**
     * Updates an existing payment provider by its unique identifier.
     */
    Mono<PaymentProviderDTO> updatePaymentProvider(Long paymentOrderId, Long paymentProviderId,
                                                   PaymentProviderDTO paymentProviderDTO);

    /**
     * Deletes a specific payment provider by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePaymentProvider(Long paymentOrderId, Long paymentProviderId);
}
