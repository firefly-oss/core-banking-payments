package com.firefly.core.banking.payments.core.services.beneficiary.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.beneficiary.v1.PaymentBeneficiaryMapper;
import com.firefly.core.banking.payments.interfaces.dtos.beneficiary.v1.PaymentBeneficiaryDTO;
import com.firefly.core.banking.payments.models.entities.beneficiary.v1.PaymentBeneficiary;
import com.firefly.core.banking.payments.models.repositories.beneficiary.v1.PaymentBeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Service
@Transactional
public class PaymentBeneficiaryManagerServiceImpl implements PaymentBeneficiaryManagerService {

    @Autowired
    private PaymentBeneficiaryRepository repository;

    @Autowired
    private PaymentBeneficiaryMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentBeneficiaryDTO>> getAllBeneficiaries(FilterRequest<PaymentBeneficiaryDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentBeneficiary.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaginationResponse<PaymentBeneficiaryDTO>> getBeneficiariesByPayerAccountId(UUID payerAccountId, FilterRequest<PaymentBeneficiaryDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentBeneficiary.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaginationResponse<PaymentBeneficiaryDTO>> getFavoriteBeneficiariesByPayerAccountId(UUID payerAccountId, FilterRequest<PaymentBeneficiaryDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentBeneficiary.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentBeneficiaryDTO> createBeneficiary(PaymentBeneficiaryDTO paymentBeneficiaryDTO) {
        PaymentBeneficiary entity = mapper.toEntity(paymentBeneficiaryDTO);
        return repository.save(entity).map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentBeneficiaryDTO> getBeneficiaryById(UUID paymentBeneficiaryId) {
        return repository.findById(paymentBeneficiaryId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentBeneficiaryDTO> updateBeneficiary(UUID paymentBeneficiaryId, PaymentBeneficiaryDTO paymentBeneficiaryDTO) {
        return repository.findById(paymentBeneficiaryId)
                .flatMap(existingEntity -> {
                    PaymentBeneficiary updatedEntity = mapper.toEntity(paymentBeneficiaryDTO);
                    updatedEntity.setPaymentBeneficiaryId(existingEntity.getPaymentBeneficiaryId());
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentBeneficiaryDTO> markAsFavorite(UUID paymentBeneficiaryId, Boolean isFavorite) {
        return repository.findById(paymentBeneficiaryId)
                .flatMap(existingEntity -> {
                    existingEntity.setIsFavorite(isFavorite);
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBeneficiary(UUID paymentBeneficiaryId) {
        return repository.deleteById(paymentBeneficiaryId);
    }
}
