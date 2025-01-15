package com.catalis.core.banking.payments.core.services.payments.swift.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftSimulationDTO;
import reactor.core.publisher.Mono;

public interface SwiftService {

    /**
     * Simulates a SWIFT payment to assess fees, availability of funds,
     * exchange rates, or any other checks.
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SwiftSimulationDTO simulationDTO);

    /**
     * Executes a SWIFT payment.
     * Typically includes final validations, OTP checks, or external SWIFT instructions.
     */
    Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SwiftExecutionDTO executionDTO);

    /**
     * Schedules recurring or delayed SWIFT payments.
     */
    Mono<PaymentOperationResponseDTO> schedulePayment(Long accountId, SwiftScheduleDTO scheduleDTO);

    /**
     * Cancels a scheduled or pending SWIFT payment.
     */
    Mono<PaymentOperationResponseDTO> cancelPayment(Long accountId, SwiftCancelDTO cancelDTO);
}

