package com.catalis.core.banking.payments.core.services.payments.sepa.v1.ict;


import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict.SepaIctExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict.SepaIctSimulationDTO;
import reactor.core.publisher.Mono;

public interface SepaIctService {

    /**
     * Simulates a SEPA ICT payment, typically checking fees, balances,
     * or other preconditions.
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SepaIctSimulationDTO simulationDTO);

    /**
     * Executes a SEPA ICT payment.
     * Usually includes final validations, OTP checks, or real-time clearing instructions.
     */
    Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SepaIctExecutionDTO executionDTO);
}