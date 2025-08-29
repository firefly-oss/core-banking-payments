package com.firefly.core.banking.payments.core.services.core.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.core.v1.PaymentOrderMapper;
import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentOrderDTO;
import com.firefly.core.banking.payments.models.entities.core.v1.PaymentOrder;
import com.firefly.core.banking.payments.models.repositories.core.v1.PaymentOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PaymentOrderManagerServiceImpl implements PaymentOrderManagerService {

    @Autowired
    private PaymentOrderRepository repository;

    @Autowired
    private PaymentOrderMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentOrderDTO>> getAllPaymentOrders(FilterRequest<PaymentOrderDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentOrder.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentOrderDTO> createPaymentOrder(PaymentOrderDTO paymentOrderDTO) {
        return Mono.just(paymentOrderDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentOrderDTO> getPaymentOrderById(Long paymentOrderId) {
        return repository.findById(paymentOrderId)
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Payment order not found.")));
    }

    @Override
    public Mono<PaymentOrderDTO> updatePaymentOrder(Long paymentOrderId, PaymentOrderDTO paymentOrderDTO) {
        return repository.findById(paymentOrderId)
                .switchIfEmpty(Mono.error(new RuntimeException("Payment order not found.")))
                .flatMap(existingOrder -> {
                    paymentOrderDTO.setPaymentOrderId(paymentOrderId);
                    return Mono.just(paymentOrderDTO)
                            .map(mapper::toEntity)
                            .flatMap(repository::save)
                            .map(mapper::toDTO);
                });
    }

    @Override
    public Mono<Void> deletePaymentOrder(Long paymentOrderId) {
        return repository.findById(paymentOrderId)
                .switchIfEmpty(Mono.error(new RuntimeException("Payment order not found.")))
                .flatMap(repository::delete)
                .then();
    }
}