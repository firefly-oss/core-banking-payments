package com.catalis.core.banking.payments.core.services.payments.internal.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class InternalPaymentServiceImpl implements InternalPaymentService{

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, InternalPaymentSimulationRequestDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, InternalPaymentExecutionRequestDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateDeletion(Long accountId, InternalPaymentSimulationRequestDTO deleteSimulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeDeletion(Long accountId, InternalPaymentExecutionRequestDTO deleteExecutionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(Long accountId, InternalPeriodicPaymentSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePeriodicPayment(Long accountId, InternalPeriodicPaymentExecutionRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(Long accountId, InternalPeriodicPaymentCancelSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeCancelPeriodic(Long accountId, InternalPeriodicPaymentCancelExecutionRequestDTO requestDTO) {
        return null;
    }

}