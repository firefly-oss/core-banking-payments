package com.catalis.core.banking.payments.core.services.payments.internal.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalSimulationDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class InternalPaymentServiceImpl implements InternalPaymentService{

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, InternalSimulationDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, InternalExecutionDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> schedulePayment(Long accountId, InternalScheduleDTO scheduleDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> cancelPayment(Long accountId, InternalCancelDTO cancelDTO) {
        return null;
    }
}