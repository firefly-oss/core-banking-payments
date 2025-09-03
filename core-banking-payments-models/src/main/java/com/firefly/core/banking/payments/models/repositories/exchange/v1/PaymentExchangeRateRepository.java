package com.firefly.core.banking.payments.models.repositories.exchange.v1;

import com.firefly.core.banking.payments.models.entities.exchange.v1.PaymentExchangeRate;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PaymentExchangeRateRepository extends BaseRepository<PaymentExchangeRate, UUID> {
    Flux<PaymentExchangeRate> findByPaymentOrderId(UUID paymentOrderId);
    Mono<PaymentExchangeRate> findFirstByPaymentOrderIdOrderByDateCreatedDesc(UUID paymentOrderId);
}
