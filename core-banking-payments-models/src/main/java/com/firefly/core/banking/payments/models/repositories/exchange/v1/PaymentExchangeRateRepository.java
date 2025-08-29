package com.firefly.core.banking.payments.models.repositories.exchange.v1;

import com.firefly.core.banking.payments.models.entities.exchange.v1.PaymentExchangeRate;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PaymentExchangeRateRepository extends R2dbcRepository<PaymentExchangeRate, Long> {
    Flux<PaymentExchangeRate> findByPaymentOrderId(Long paymentOrderId);
    Mono<PaymentExchangeRate> findFirstByPaymentOrderIdOrderByDateCreatedDesc(Long paymentOrderId);
}
