package com.firefly.core.banking.payments.core.services.payroll.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.payroll.v1.PayrollOrderMapper;
import com.firefly.core.banking.payments.interfaces.dtos.payroll.v1.PayrollOrderDTO;
import com.firefly.core.banking.payments.models.entities.payroll.v1.PayrollOrder;
import com.firefly.core.banking.payments.models.repositories.payroll.v1.PayrollOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PayrollOrderManagerServiceImpl implements PayrollOrderManagerService {

    @Autowired
    private PayrollOrderRepository repository;

    @Autowired
    private PayrollOrderMapper mapper;

    @Override
    public Mono<PaginationResponse<PayrollOrderDTO>> getAllPayrollOrders(Long paymentOrderId, FilterRequest<PayrollOrderDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PayrollOrder.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PayrollOrderDTO> createPayrollOrder(Long paymentOrderId, PayrollOrderDTO payrollOrderDTO) {
        payrollOrderDTO.setPaymentOrderId(paymentOrderId);
        PayrollOrder payrollOrder = mapper.toEntity(payrollOrderDTO);
        return Mono.just(payrollOrder)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PayrollOrderDTO> getPayrollOrderById(Long paymentOrderId, Long payrollOrderId) {
        return repository.findById(payrollOrderId)
                .filter(order -> order.getPaymentOrderId().equals(paymentOrderId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PayrollOrderDTO> updatePayrollOrder(Long paymentOrderId, Long payrollOrderId, PayrollOrderDTO payrollOrderDTO) {
        return repository.findById(payrollOrderId)
                .filter(order -> order.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(existingOrder -> {
                    PayrollOrder updatedOrder = mapper.toEntity(payrollOrderDTO);
                    updatedOrder.setPayrollOrderId(existingOrder.getPayrollOrderId());
                    updatedOrder.setPaymentOrderId(existingOrder.getPaymentOrderId());
                    return repository.save(updatedOrder);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePayrollOrder(Long paymentOrderId, Long payrollOrderId) {
        return repository.findById(payrollOrderId)
                .filter(order -> order.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(repository::delete);
    }
}