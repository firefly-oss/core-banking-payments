package com.firefly.core.banking.payments.core.services.exchange.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.exchange.v1.PaymentExchangeRateDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentExchangeRateManagerService {
    
    Mono<PaginationResponse<PaymentExchangeRateDTO>> getExchangeRatesByPaymentOrderId(UUID paymentOrderId, FilterRequest<PaymentExchangeRateDTO> filterRequest);
    
    Mono<PaymentExchangeRateDTO> createExchangeRate(UUID paymentOrderId, PaymentExchangeRateDTO paymentExchangeRateDTO);
    
    Mono<PaymentExchangeRateDTO> getExchangeRateById(UUID paymentExchangeRateId);
    
    Mono<PaymentExchangeRateDTO> updateExchangeRate(UUID paymentOrderId, UUID paymentExchangeRateId, PaymentExchangeRateDTO paymentExchangeRateDTO);
    
    Mono<Void> deleteExchangeRate(UUID paymentExchangeRateId);
    
    Mono<PaymentExchangeRateDTO> getCurrentExchangeRate(String sourceCurrency, String targetCurrency);
}
