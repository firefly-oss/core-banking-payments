package com.catalis.core.banking.payments.core.services.payments.sepa.v1.sct;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctSimulationDTO;
import reactor.core.publisher.Mono;

public interface SepaSctService {

    /**
     * Simulate a SEPA SCT payment to estimate fees, check balances, etc.
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SepaSctSimulationDTO simulationDTO);

    /**
     * Execute a SEPA SCT payment, typically finalizing checks or OTP validations.
     */
    Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SepaSctExecutionDTO executionDTO);

    /**
     * Schedule recurring or delayed SEPA SCT payments.
     */
    Mono<PaymentOperationResponseDTO> schedulePayment(Long accountId, SepaSctScheduleDTO scheduleDTO);

    /**
     * Cancel a scheduled or pending SEPA SCT payment operation.
     */
    Mono<PaymentOperationResponseDTO> cancelPayment(Long accountId, SepaSctCancelDTO cancelDTO);
}