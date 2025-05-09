package com.catalis.core.banking.payments.models.repositories.audit.v1;

import com.catalis.core.banking.payments.models.entities.audit.v1.PaymentAudit;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PaymentAuditRepository extends R2dbcRepository<PaymentAudit, Long> {
    Flux<PaymentAudit> findByPaymentOrderId(Long paymentOrderId);
    Flux<PaymentAudit> findByPaymentInstructionId(Long paymentInstructionId);
}
