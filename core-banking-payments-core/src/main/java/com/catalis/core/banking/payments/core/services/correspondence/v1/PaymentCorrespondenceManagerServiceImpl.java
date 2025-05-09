package com.catalis.core.banking.payments.core.services.correspondence.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.mappers.correspondence.v1.PaymentCorrespondenceMapper;
import com.catalis.core.banking.payments.interfaces.dtos.correspondence.v1.PaymentCorrespondenceDTO;
import com.catalis.core.banking.payments.models.entities.correspondence.v1.PaymentCorrespondence;
import com.catalis.core.banking.payments.models.repositories.correspondence.v1.PaymentCorrespondenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PaymentCorrespondenceManagerServiceImpl implements PaymentCorrespondenceManagerService {

    @Autowired
    private PaymentCorrespondenceRepository repository;

    @Autowired
    private PaymentCorrespondenceMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentCorrespondenceDTO>> getCorrespondenceByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentCorrespondenceDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentCorrespondence.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaginationResponse<PaymentCorrespondenceDTO>> getCorrespondenceByPaymentOrderIdAndType(Long paymentOrderId, String correspondenceType, FilterRequest<PaymentCorrespondenceDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentCorrespondence.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentCorrespondenceDTO> createCorrespondence(Long paymentOrderId, PaymentCorrespondenceDTO paymentCorrespondenceDTO) {
        paymentCorrespondenceDTO.setPaymentOrderId(paymentOrderId);
        PaymentCorrespondence entity = mapper.toEntity(paymentCorrespondenceDTO);
        return repository.save(entity).map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentCorrespondenceDTO> getCorrespondenceById(Long paymentCorrespondenceId) {
        return repository.findById(paymentCorrespondenceId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentCorrespondenceDTO> updateCorrespondence(Long paymentOrderId, Long paymentCorrespondenceId, PaymentCorrespondenceDTO paymentCorrespondenceDTO) {
        return repository.findById(paymentCorrespondenceId)
                .flatMap(existingEntity -> {
                    PaymentCorrespondence updatedEntity = mapper.toEntity(paymentCorrespondenceDTO);
                    updatedEntity.setPaymentCorrespondenceId(existingEntity.getPaymentCorrespondenceId());
                    updatedEntity.setPaymentOrderId(paymentOrderId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCorrespondence(Long paymentCorrespondenceId) {
        return repository.deleteById(paymentCorrespondenceId);
    }
}
