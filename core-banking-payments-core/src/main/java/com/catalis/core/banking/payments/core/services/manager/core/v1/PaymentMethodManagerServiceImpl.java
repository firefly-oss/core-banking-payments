package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.mappers.manager.core.v1.PaymentMethodMapper;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentMethodDTO;
import com.catalis.core.banking.payments.models.entities.manager.core.v1.PaymentMethod;
import com.catalis.core.banking.payments.models.repositories.manager.core.v1.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PaymentMethodManagerServiceImpl implements PaymentMethodManagerService {

    @Autowired
    private PaymentMethodRepository repository;

    @Autowired
    private PaymentMethodMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentMethodDTO>> getAllPaymentMethods(FilterRequest<PaymentMethodDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentMethod.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentMethodDTO> createPaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod entity = mapper.toEntity(paymentMethodDTO);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentMethodDTO> getPaymentMethodById(Long paymentMethodId) {
        return repository.findById(paymentMethodId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentMethodDTO> updatePaymentMethod(Long paymentMethodId, PaymentMethodDTO paymentMethodDTO) {
        return repository.findById(paymentMethodId)
                .flatMap(existingEntity -> {
                    existingEntity.setMethodName(paymentMethodDTO.getMethodName());
                    existingEntity.setDescription(paymentMethodDTO.getDescription());
                    existingEntity.setActiveFlag(paymentMethodDTO.getActiveFlag());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePaymentMethod(Long paymentMethodId) {
        return repository.findById(paymentMethodId)
                .flatMap(repository::delete);
    }
}