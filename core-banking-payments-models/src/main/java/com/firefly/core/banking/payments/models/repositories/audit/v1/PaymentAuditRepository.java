package com.firefly.core.banking.payments.models.repositories.audit.v1;

import com.firefly.core.banking.payments.models.entities.audit.v1.PaymentAudit;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface PaymentAuditRepository extends BaseRepository<PaymentAudit, UUID> {
    Flux<PaymentAudit> findByPaymentOrderId(UUID paymentOrderId);
    Flux<PaymentAudit> findByPaymentInstructionId(UUID paymentInstructionId);
}
