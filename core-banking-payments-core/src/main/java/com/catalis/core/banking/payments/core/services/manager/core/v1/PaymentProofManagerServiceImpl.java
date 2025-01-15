package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.mappers.manager.core.v1.PaymentProofMapper;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentProofDTO;
import com.catalis.core.banking.payments.models.entities.manager.core.v1.PaymentProof;
import com.catalis.core.banking.payments.models.repositories.manager.core.v1.PaymentProofRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PaymentProofManagerServiceImpl implements PaymentProofManagerService {

    @Autowired
    private PaymentProofRepository repository;

    @Autowired
    private PaymentProofMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentProofDTO>> getAllPaymentProofs(Long paymentOrderId, FilterRequest<PaymentProofDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentProof.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentProofDTO> createPaymentProof(Long paymentOrderId, PaymentProofDTO paymentProofDTO) {
        PaymentProof paymentProof = mapper.toEntity(paymentProofDTO);
        paymentProof.setPaymentOrderId(paymentOrderId);
        return Mono.just(paymentProof)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentProofDTO> getPaymentProofById(Long paymentOrderId, Long paymentProofId) {
        return repository.findById(paymentProofId)
                .filter(entity -> entity.getPaymentOrderId().equals(paymentOrderId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentProofDTO> updatePaymentProof(Long paymentOrderId, Long paymentProofId, PaymentProofDTO paymentProofDTO) {
        return repository.findById(paymentProofId)
                .filter(entity -> entity.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(existingProof -> {
                    PaymentProof updatedProof = mapper.toEntity(paymentProofDTO);
                    updatedProof.setPaymentProofId(existingProof.getPaymentProofId());
                    updatedProof.setPaymentOrderId(existingProof.getPaymentOrderId());
                    return repository.save(updatedProof);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePaymentProof(Long paymentOrderId, Long paymentProofId) {
        return repository.findById(paymentProofId)
                .filter(entity -> entity.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(repository::delete);
    }
}
