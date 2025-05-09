package com.catalis.core.banking.payments.models.repositories.beneficiary.v1;

import com.catalis.core.banking.payments.models.entities.beneficiary.v1.PaymentBeneficiary;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PaymentBeneficiaryRepository extends R2dbcRepository<PaymentBeneficiary, Long> {
    Flux<PaymentBeneficiary> findByPayerAccountId(Long payerAccountId);
    Flux<PaymentBeneficiary> findByPayerAccountIdAndIsFavoriteTrue(Long payerAccountId);
    Mono<PaymentBeneficiary> findByPayerAccountIdAndBeneficiaryIban(Long payerAccountId, String beneficiaryIban);
}
