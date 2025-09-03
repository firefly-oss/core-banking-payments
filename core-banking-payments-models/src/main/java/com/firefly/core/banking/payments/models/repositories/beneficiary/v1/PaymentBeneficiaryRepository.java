package com.firefly.core.banking.payments.models.repositories.beneficiary.v1;

import com.firefly.core.banking.payments.models.entities.beneficiary.v1.PaymentBeneficiary;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PaymentBeneficiaryRepository extends BaseRepository<PaymentBeneficiary, UUID> {
    Flux<PaymentBeneficiary> findByPayerAccountId(UUID payerAccountId);
    Flux<PaymentBeneficiary> findByPayerAccountIdAndIsFavoriteTrue(UUID payerAccountId);
    Mono<PaymentBeneficiary> findByPayerAccountIdAndBeneficiaryIban(UUID payerAccountId, String beneficiaryIban);
}
