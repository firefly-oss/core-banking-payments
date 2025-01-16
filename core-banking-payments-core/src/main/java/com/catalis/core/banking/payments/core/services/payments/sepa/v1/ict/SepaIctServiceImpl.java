package com.catalis.core.banking.payments.core.services.payments.sepa.v1.ict;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SepaIctServiceImpl implements SepaIctService {

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SepaIctPaymentSimulationRequestDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SepaIctPaymentExecutionRequestDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateDeletion(Long accountId, SepaIctPaymentSimulationRequestDTO deleteSimulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeDeletion(Long accountId, SepaIctPaymentExecutionRequestDTO deleteExecutionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(Long accountId, SepaIctPeriodicPaymentSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePeriodicPayment(Long accountId, SepaIctPeriodicPaymentExecutionRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(Long accountId, SepaIctPeriodicPaymentCancelSimulationRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executeCancelPeriodic(Long accountId, SepaIctPeriodicPaymentCancelExecutionRequestDTO requestDTO) {
        return null;
    }

}