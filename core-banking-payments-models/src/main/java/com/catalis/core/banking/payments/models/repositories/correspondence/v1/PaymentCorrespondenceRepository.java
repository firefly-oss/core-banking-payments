package com.catalis.core.banking.payments.models.repositories.correspondence.v1;

import com.catalis.core.banking.payments.models.entities.correspondence.v1.PaymentCorrespondence;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PaymentCorrespondenceRepository extends R2dbcRepository<PaymentCorrespondence, Long> {
    Flux<PaymentCorrespondence> findByPaymentOrderId(Long paymentOrderId);
    Flux<PaymentCorrespondence> findByPaymentOrderIdAndCorrespondenceType(Long paymentOrderId, String correspondenceType);
}
