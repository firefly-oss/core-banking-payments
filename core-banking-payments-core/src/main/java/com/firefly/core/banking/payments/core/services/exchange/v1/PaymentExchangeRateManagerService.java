package com.firefly.core.banking.payments.core.services.exchange.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.exchange.v1.PaymentExchangeRateDTO;
import reactor.core.publisher.Mono;

public interface PaymentExchangeRateManagerService {
    
    Mono<PaginationResponse<PaymentExchangeRateDTO>> getExchangeRatesByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentExchangeRateDTO> filterRequest);
    
    Mono<PaymentExchangeRateDTO> createExchangeRate(Long paymentOrderId, PaymentExchangeRateDTO paymentExchangeRateDTO);
    
    Mono<PaymentExchangeRateDTO> getExchangeRateById(Long paymentExchangeRateId);
    
    Mono<PaymentExchangeRateDTO> updateExchangeRate(Long paymentOrderId, Long paymentExchangeRateId, PaymentExchangeRateDTO paymentExchangeRateDTO);
    
    Mono<Void> deleteExchangeRate(Long paymentExchangeRateId);
    
    Mono<PaymentExchangeRateDTO> getCurrentExchangeRate(String sourceCurrency, String targetCurrency);
}
