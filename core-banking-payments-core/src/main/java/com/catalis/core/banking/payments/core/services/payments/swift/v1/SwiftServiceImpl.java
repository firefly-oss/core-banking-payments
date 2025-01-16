package com.catalis.core.banking.payments.core.services.payments.swift.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SwiftServiceImpl implements SwiftService {

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SwiftPaymentSimulationRequestDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SwiftPaymentExecutionRequestDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateDeletion(Long accountId, SwiftPaymentSimulationRequestDTO deleteSimulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeDeletion(Long accountId, SwiftPaymentExecutionRequestDTO deleteExecutionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(Long accountId, SwiftPeriodicPaymentSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePeriodicPayment(Long accountId, SwiftPeriodicPaymentExecutionRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(Long accountId, SwiftPeriodicPaymentCancelSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeCancelPeriodic(Long accountId, SwiftPeriodicPaymentCancelExecutionRequestDTO requestDTO) {
        return null;
    }

}