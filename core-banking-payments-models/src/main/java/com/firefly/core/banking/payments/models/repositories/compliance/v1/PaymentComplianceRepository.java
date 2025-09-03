package com.firefly.core.banking.payments.models.repositories.compliance.v1;

import com.firefly.core.banking.payments.models.entities.compliance.v1.PaymentCompliance;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PaymentComplianceRepository extends BaseRepository<PaymentCompliance, UUID> {
    Flux<PaymentCompliance> findByPaymentOrderId(UUID paymentOrderId);
    Mono<PaymentCompliance> findFirstByPaymentOrderIdOrderByDateCreatedDesc(UUID paymentOrderId);
}
