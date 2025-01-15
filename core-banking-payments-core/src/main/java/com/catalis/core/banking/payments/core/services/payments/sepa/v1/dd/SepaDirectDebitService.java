package com.catalis.core.banking.payments.core.services.payments.sepa.v1.dd;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitSimulationDTO;
import reactor.core.publisher.Mono;

public interface SepaDirectDebitService {

    /**
     * Simulates a SEPA Direct Debit payment to estimate fees,
     * verify balances, or perform other checks.
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SepaDirectDebitSimulationDTO simulationDTO);

    /**
     * Executes a SEPA Direct Debit payment.
     * Typically involves final validations, possibly OTP checks.
     */
    Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SepaDirectDebitExecutionDTO executionDTO);

    /**
     * Schedules recurring or deferred SEPA Direct Debit payments.
     */
    Mono<PaymentOperationResponseDTO> schedulePayment(Long accountId, SepaDirectDebitScheduleDTO scheduleDTO);

    /**
     * Cancels a scheduled or pending SEPA Direct Debit payment.
     */
    Mono<PaymentOperationResponseDTO> cancelPayment(Long accountId, SepaDirectDebitCancelDTO cancelDTO);
}

