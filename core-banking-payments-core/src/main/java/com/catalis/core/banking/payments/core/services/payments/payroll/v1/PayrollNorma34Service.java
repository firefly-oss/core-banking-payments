package com.catalis.core.banking.payments.core.services.payments.payroll.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34CancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34ExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34ScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34SimulationDTO;
import reactor.core.publisher.Mono;

public interface PayrollNorma34Service {

    /**
     * Simulate a payroll operation (Norma 34) to estimate fees,
     * check balances, or generate a preliminary file, etc.
     */
    Mono<PaymentOperationResponseDTO> simulatePayroll(Long accountId, PayrollNorma34SimulationDTO simulationDTO);

    /**
     * Execute a payroll operation (Norma 34). Typically requires
     * final validations, OTP, or file generation processes.
     */
    Mono<PaymentOperationResponseDTO> executePayroll(Long accountId, PayrollNorma34ExecutionDTO executionDTO);

    /**
     * Schedule recurring or delayed payroll operations (Norma 34).
     */
    Mono<PaymentOperationResponseDTO> schedulePayroll(Long accountId, PayrollNorma34ScheduleDTO scheduleDTO);

    /**
     * Cancel a scheduled or pending payroll operation (Norma 34).
     */
    Mono<PaymentOperationResponseDTO> cancelPayroll(Long accountId, PayrollNorma34CancelDTO cancelDTO);
}