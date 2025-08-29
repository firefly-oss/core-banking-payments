package com.firefly.core.banking.payments.core.services.core.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.core.v1.PaymentFeeMapper;
import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentFeeDTO;
import com.firefly.core.banking.payments.models.entities.core.v1.PaymentFee;
import com.firefly.core.banking.payments.models.repositories.core.v1.PaymentFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PaymentFeeManagerServiceImpl implements PaymentFeeManagerService {

    @Autowired
    private PaymentFeeRepository repository;

    @Autowired
    private PaymentFeeMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentFeeDTO>> getAllPaymentFees(Long paymentOrderId, FilterRequest<PaymentFeeDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentFee.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentFeeDTO> createPaymentFee(Long paymentOrderId, PaymentFeeDTO paymentFeeDTO) {
        paymentFeeDTO.setPaymentOrderId(paymentOrderId);
        PaymentFee entity = mapper.toEntity(paymentFeeDTO);
        return repository.save(entity).map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentFeeDTO> getPaymentFeeById(Long paymentOrderId, Long paymentFeeId) {
        return repository.findById(paymentFeeId)
                .filter(paymentFee -> paymentFee.getPaymentOrderId().equals(paymentOrderId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentFeeDTO> updatePaymentFee(Long paymentOrderId, Long paymentFeeId, PaymentFeeDTO paymentFeeDTO) {
        return repository.findById(paymentFeeId)
                .filter(paymentFee -> paymentFee.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(existingPaymentFee -> {
                    PaymentFee updatedEntity = mapper.toEntity(paymentFeeDTO);
                    updatedEntity.setPaymentFeeId(paymentFeeId);
                    updatedEntity.setPaymentOrderId(paymentOrderId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePaymentFee(Long paymentOrderId, Long paymentFeeId) {
        return repository.findById(paymentFeeId)
                .filter(paymentFee -> paymentFee.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(repository::delete);
    }
}