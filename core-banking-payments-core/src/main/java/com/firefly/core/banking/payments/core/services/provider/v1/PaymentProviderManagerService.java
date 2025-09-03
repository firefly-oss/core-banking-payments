package com.firefly.core.banking.payments.core.services.provider.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.provider.v1.PaymentProviderDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentProviderManagerService {

    /**
     * Retrieves a paginated list of payment providers for a given payment order.
     */
    Mono<PaginationResponse<PaymentProviderDTO>> getAllPaymentProviders(UUID paymentOrderId,
                                                                        FilterRequest<PaymentProviderDTO> filterRequest);

    /**
     * Creates a new payment provider for the specified payment order.
     */
    Mono<PaymentProviderDTO> createPaymentProvider(UUID paymentOrderId, PaymentProviderDTO paymentProviderDTO);

    /**
     * Retrieves a specific payment provider by its unique identifier, within the context of a payment order.
     */
    Mono<PaymentProviderDTO> getPaymentProviderById(UUID paymentOrderId, UUID paymentProviderId);

    /**
     * Updates an existing payment provider by its unique identifier.
     */
    Mono<PaymentProviderDTO> updatePaymentProvider(UUID paymentOrderId, UUID paymentProviderId,
                                                   PaymentProviderDTO paymentProviderDTO);

    /**
     * Deletes a specific payment provider by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePaymentProvider(UUID paymentOrderId, UUID paymentProviderId);
}
