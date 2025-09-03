package com.firefly.core.banking.payments.core.services.core.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.core.v1.PaymentScheduleMapper;
import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentScheduleDTO;
import com.firefly.core.banking.payments.models.entities.core.v1.PaymentSchedule;
import com.firefly.core.banking.payments.models.repositories.core.v1.PaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Service
@Transactional
public class PaymentScheduleManagerServiceImpl implements PaymentScheduleManagerService {

    @Autowired
    private PaymentScheduleRepository repository;

    @Autowired
    private PaymentScheduleMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentScheduleDTO>> getAllPaymentSchedules(UUID paymentOrderId, FilterRequest<PaymentScheduleDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentSchedule.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentScheduleDTO> createPaymentSchedule(UUID paymentOrderId, PaymentScheduleDTO paymentScheduleDTO) {
        paymentScheduleDTO.setPaymentOrderId(paymentOrderId);
        PaymentSchedule entity = mapper.toEntity(paymentScheduleDTO);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentScheduleDTO> getPaymentScheduleById(UUID paymentOrderId, UUID paymentScheduleId) {
        return repository.findById(paymentScheduleId)
                .filter(paymentSchedule -> paymentSchedule.getPaymentOrderId().equals(paymentOrderId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentScheduleDTO> updatePaymentSchedule(UUID paymentOrderId, UUID paymentScheduleId, PaymentScheduleDTO paymentScheduleDTO) {
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
    public Mono<Void> deletePaymentSchedule(UUID paymentOrderId, UUID paymentScheduleId) {
        return repository.findById(paymentScheduleId)
                .filter(paymentSchedule -> paymentSchedule.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(repository::delete);
    }
}