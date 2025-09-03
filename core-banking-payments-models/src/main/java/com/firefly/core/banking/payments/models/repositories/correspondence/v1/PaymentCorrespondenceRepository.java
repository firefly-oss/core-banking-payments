package com.firefly.core.banking.payments.models.repositories.correspondence.v1;

import com.firefly.core.banking.payments.models.entities.correspondence.v1.PaymentCorrespondence;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface PaymentCorrespondenceRepository extends BaseRepository<PaymentCorrespondence, UUID> {
    Flux<PaymentCorrespondence> findByPaymentOrderId(UUID paymentOrderId);
    Flux<PaymentCorrespondence> findByPaymentOrderIdAndCorrespondenceType(UUID paymentOrderId, String correspondenceType);
}
