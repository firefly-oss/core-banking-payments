package com.catalis.core.banking.payments.core.services.payments.sepa.v1.dd;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SepaDirectDebitServiceImpl implements SepaDirectDebitService {

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, DirectDebitPaymentSimulationRequestDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, DirectDebitPaymentExecutionRequestDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateDeletion(Long accountId, DirectDebitPaymentSimulationRequestDTO deleteSimulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeDeletion(Long accountId, DirectDebitPaymentExecutionRequestDTO deleteExecutionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(Long accountId, DirectDebitPeriodicPaymentSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePeriodicPayment(Long accountId, DirectDebitPeriodicPaymentExecutionRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(Long accountId, DirectDebitPeriodicPaymentCancelSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeCancelPeriodic(Long accountId, DirectDebitPeriodicPaymentCancelExecutionRequestDTO requestDTO) {
        return null;
    }

}
