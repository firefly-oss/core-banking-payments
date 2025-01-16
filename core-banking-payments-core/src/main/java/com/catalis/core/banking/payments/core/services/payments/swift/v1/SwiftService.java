package com.catalis.core.banking.payments.core.services.payments.swift.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.*;
import reactor.core.publisher.Mono;

/**
 * Service interface for handling SWIFT payment operations:
 * simulation, execution, and two-step deletion (simulate & execute).
 */
public interface SwiftService {

    /**
     * Simulates a SWIFT payment (e.g., to trigger OTP if required).
     *
     * @param accountId     The debtor's account ID (path variable).
     * @param simulationDTO Details about the SWIFT payment to simulate.
     * @return A reactive Mono containing the PaymentOperationResponseDTO,
     *         typically with a status indicating PENDING_OTP or SIMULATION_SUCCESS.
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(
            Long accountId,
            SwiftPaymentSimulationRequestDTO simulationDTO
    );

    /**
     * Executes a SWIFT payment (consuming OTP if required).
     *
     * @param accountId    The debtor's account ID (path variable).
     * @param executionDTO Details about the payment, including the OTP if required.
     * @return A reactive Mono containing the PaymentOperationResponseDTO,
     *         typically with a status indicating SUCCESS or FAILURE.
     */
    Mono<PaymentOperationResponseDTO> executePayment(
            Long accountId,
            SwiftPaymentExecutionRequestDTO executionDTO
    );

    /**
     * Simulates deletion (cancellation) of a SWIFT payment.
     * Typically triggers an OTP if SCA is required for cancellation.
     *
     * @param accountId           The debtor's account ID (path variable).
     * @param deleteSimulationDTO Details identifying the payment to be deleted.
     * @return A reactive Mono containing the PaymentOperationResponseDTO,
     *         often with a status = PENDING_OTP indicating that
     *         the user must provide OTP in a subsequent step.
     */
    Mono<PaymentOperationResponseDTO> simulateDeletion(
            Long accountId,
            SwiftPaymentSimulationRequestDTO deleteSimulationDTO
    );

    /**
     * Executes the deletion of a SWIFT payment (consuming OTP if required).
     *
     * @param accountId          The debtor's account ID (path variable).
     * @param deleteExecutionDTO Details (including OTP) required to finalize the deletion.
     * @return A reactive Mono containing the PaymentOperationResponseDTO,
     *         typically indicating success/failure of the deletion.
     */
    Mono<PaymentOperationResponseDTO> executeDeletion(
            Long accountId,
            SwiftPaymentExecutionRequestDTO deleteExecutionDTO
    );

    // PERIODIC creation
    Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(
            Long accountId,
            SwiftPeriodicPaymentSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executePeriodicPayment(
            Long accountId,
            SwiftPeriodicPaymentExecutionRequestDTO requestDTO
    );

    // PERIODIC cancellation
    Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(
            Long accountId,
            SwiftPeriodicPaymentCancelSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executeCancelPeriodic(
            Long accountId,
            SwiftPeriodicPaymentCancelExecutionRequestDTO requestDTO
    );
}

