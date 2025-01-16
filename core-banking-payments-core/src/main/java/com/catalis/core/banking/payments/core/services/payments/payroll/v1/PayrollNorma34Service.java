package com.catalis.core.banking.payments.core.services.payments.payroll.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.*;
import reactor.core.publisher.Mono;

/**
 * Service interface for handling Norma 34 payroll operations,
 * such as simulation, execution, scheduling, cancellation,
 * and deletion (simulate & execute).
 */
public interface PayrollNorma34Service {

    /**
     * Simulates a payroll (Norma 34) operation.
     * Triggers OTP if SCA is required.
     *
     * @param accountId       The employer's (debtor) account ID.
     * @param simulationDTO   The details of the payroll to simulate.
     * @return A Mono of PaymentOperationResponseDTO with simulation results.
     */
    Mono<PaymentOperationResponseDTO> simulatePayroll(
            Long accountId,
            Norma34PaymentSimulationRequestDTO simulationDTO
    );

    /**
     * Executes a payroll (Norma 34) transaction.
     * Consumes OTP if required.
     *
     * @param accountId      The employer's (debtor) account ID.
     * @param executionDTO   The details of the payroll to execute.
     * @return A Mono of PaymentOperationResponseDTO with execution results.
     */
    Mono<PaymentOperationResponseDTO> executePayroll(
            Long accountId,
            Norma34PaymentExecutionRequestDTO executionDTO
    );

    /**
     * Simulates the deletion of a payroll instruction (e.g., a periodic or scheduled payroll).
     * Typically triggers OTP for confirmation.
     *
     * @param accountId          The employer's account ID.
     * @param deleteSimulationDTO The details identifying the payroll to be deleted.
     * @return A Mono of PaymentOperationResponseDTO (likely "PENDING_OTP").
     */
    Mono<PaymentOperationResponseDTO> simulateDeletion(
            Long accountId,
            Norma34PaymentSimulationRequestDTO deleteSimulationDTO
    );

    /**
     * Executes the actual deletion of a payroll instruction.
     * Consumes OTP.
     *
     * @param accountId         The employer's account ID.
     * @param deleteExecutionDTO The details (including OTP) to finalize deletion.
     * @return A Mono of PaymentOperationResponseDTO with final deletion status.
     */
    Mono<PaymentOperationResponseDTO> executeDeletion(
            Long accountId,
            Norma34PaymentExecutionRequestDTO deleteExecutionDTO
    );

    // PERIODIC creation
    Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(
            Long accountId,
            Norma34PeriodicPaymentSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executePeriodicPayment(
            Long accountId,
            Norma34PeriodicPaymentExecutionRequestDTO requestDTO
    );

    // PERIODIC cancellation
    Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(
            Long accountId,
            Norma34PeriodicPaymentCancelSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executeCancelPeriodic(
            Long accountId,
            Norma34PeriodicPaymentCancelExecutionRequestDTO requestDTO
    );
}
