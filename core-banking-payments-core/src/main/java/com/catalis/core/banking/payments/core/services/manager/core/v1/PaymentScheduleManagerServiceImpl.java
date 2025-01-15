package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.mappers.manager.core.v1.PaymentScheduleMapper;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentScheduleDTO;
import com.catalis.core.banking.payments.models.entities.manager.core.v1.PaymentSchedule;
import com.catalis.core.banking.payments.models.repositories.manager.core.v1.PaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PaymentScheduleManagerServiceImpl implements PaymentScheduleManagerService {

    @Autowired
    private PaymentScheduleRepository repository;

    @Autowired
    private PaymentScheduleMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentScheduleDTO>> getAllPaymentSchedules(Long paymentOrderId, FilterRequest<PaymentScheduleDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentSchedule.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentScheduleDTO> createPaymentSchedule(Long paymentOrderId, PaymentScheduleDTO paymentScheduleDTO) {
        paymentScheduleDTO.setPaymentOrderId(paymentOrderId);
        PaymentSchedule entity = mapper.toEntity(paymentScheduleDTO);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentScheduleDTO> getPaymentScheduleById(Long paymentOrderId, Long paymentScheduleId) {
        return repository.findById(paymentScheduleId)
                .filter(paymentSchedule -> paymentSchedule.getPaymentOrderId().equals(paymentOrderId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentScheduleDTO> updatePaymentSchedule(Long paymentOrderId, Long paymentScheduleId, PaymentScheduleDTO paymentScheduleDTO) {
        return repository.findById(paymentScheduleId)
                .filter(paymentSchedule -> paymentSchedule.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(existingEntity -> {
                    PaymentSchedule updatedEntity = mapper.toEntity(paymentScheduleDTO);
                    updatedEntity.setPaymentScheduleId(existingEntity.getPaymentScheduleId());
                    updatedEntity.setPaymentOrderId(existingEntity.getPaymentOrderId());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePaymentSchedule(Long paymentOrderId, Long paymentScheduleId) {
        return repository.findById(paymentScheduleId)
                .filter(paymentSchedule -> paymentSchedule.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(repository::delete);
    }
}