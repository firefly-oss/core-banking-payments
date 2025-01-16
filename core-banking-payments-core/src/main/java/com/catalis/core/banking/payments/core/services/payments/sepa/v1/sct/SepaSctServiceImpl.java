package com.catalis.core.banking.payments.core.services.payments.sepa.v1.sct;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SepaSctServiceImpl implements SepaSctService {

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SepaSctPaymentSimulationRequestDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SepaSctPaymentExecutionRequestDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateDeletion(Long accountId, SepaSctPaymentSimulationRequestDTO deleteSimulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeDeletion(Long accountId, SepaSctPaymentExecutionRequestDTO deleteExecutionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(Long accountId, SepaSctPeriodicPaymentSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePeriodicPayment(Long accountId, SepaSctPeriodicPaymentExecutionRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(Long accountId, SepaSctPeriodicPaymentCancelSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeCancelPeriodic(Long accountId, SepaSctPeriodicPaymentCancelExecutionRequestDTO requestDTO) {
        return null;
    }

}