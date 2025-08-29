package com.firefly.core.banking.payments.models.repositories.compliance.v1;

import com.firefly.core.banking.payments.models.entities.compliance.v1.PaymentCompliance;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PaymentComplianceRepository extends R2dbcRepository<PaymentCompliance, Long> {
    Flux<PaymentCompliance> findByPaymentOrderId(Long paymentOrderId);
    Mono<PaymentCompliance> findFirstByPaymentOrderIdOrderByDateCreatedDesc(Long paymentOrderId);
}
