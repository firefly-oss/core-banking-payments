package com.catalis.core.banking.payments.core.services.payments.internal.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalSimulationDTO;
import reactor.core.publisher.Mono;

public interface InternalPaymentService {

    /**
     * Simulate an internal payment to evaluate fees, check balances, etc.
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, InternalSimulationDTO simulationDTO);

    /**
     * Execute an internal payment. Typically includes OTP validation or final checks.
     */
    Mono<PaymentOperationResponseDTO> executePayment(Long accountId, InternalExecutionDTO executionDTO);

    /**
     * Schedule recurring or delayed internal payments.
     */
    Mono<PaymentOperationResponseDTO> schedulePayment(Long accountId, InternalScheduleDTO scheduleDTO);

    /**
     * Cancel a scheduled or pending internal payment operation.
     */
    Mono<PaymentOperationResponseDTO> cancelPayment(Long accountId, InternalCancelDTO cancelDTO);
}