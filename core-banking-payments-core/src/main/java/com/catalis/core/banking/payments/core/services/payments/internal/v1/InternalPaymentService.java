package com.catalis.core.banking.payments.core.services.payments.internal.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.*;
import reactor.core.publisher.Mono;

/**
 * Service interface for handling Internal Payment operations
 * such as simulation, execution, and deletion.
 */
public interface InternalPaymentService {

    /**
     * Simulates an internal payment (execution, schedule, or periodic).
     * Typically triggers OTP if required.
     *
     * @param accountId       The debtor's account ID (path variable).
     * @param simulationDTO   Contains details about the payment to simulate.
     * @return A reactive Mono containing the PaymentOperationResponseDTO.
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, InternalPaymentSimulationRequestDTO simulationDTO);

    /**
     * Executes an internal payment (immediate, scheduled, or periodic creation).
     * Typically consumes an OTP if required.
     *
     * @param accountId     The debtor's account ID (path variable).
     * @param executionDTO  Contains details about the payment to execute (including OTP).
     * @return A reactive Mono containing the PaymentOperationResponseDTO.
     */
    Mono<PaymentOperationResponseDTO> executePayment(Long accountId, InternalPaymentExecutionRequestDTO executionDTO);

    /**
     * Simulates the deletion of an internal payment (e.g., a scheduled or periodic payment).
     * Typically triggers OTP for deletion confirmation.
     *
     * @param accountId          The debtor's account ID (path variable).
     * @param deleteSimulationDTO Contains details (including the existing payment reference)
     *                            for the payment to be deleted.
     * @return A reactive Mono containing the PaymentOperationResponseDTO,
     *         likely with status = "PENDING_OTP" if OTP is required.
     */
    Mono<PaymentOperationResponseDTO> simulateDeletion(Long accountId, InternalPaymentSimulationRequestDTO deleteSimulationDTO);

    /**
     * Executes the actual deletion of an internal payment.
     * Consumes the OTP that was triggered by simulateDeletion.
     *
     * @param accountId         The debtor's account ID (path variable).
     * @param deleteExecutionDTO Contains details (including OTP) to finalize deletion.
     * @return A reactive Mono containing the PaymentOperationResponseDTO,
     *         typically reflecting success or failure of the deletion.
     */
    Mono<PaymentOperationResponseDTO> executeDeletion(Long accountId, InternalPaymentExecutionRequestDTO deleteExecutionDTO);

    /**
     * Simulate the creation of a periodic internal payment (triggers OTP).
     */
    Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(
            Long accountId,
            InternalPeriodicPaymentSimulationRequestDTO requestDTO
    );

    /**
     * Execute (finalize) the creation of a periodic internal payment,
     * consuming the OTP.
     */
    Mono<PaymentOperationResponseDTO> executePeriodicPayment(
            Long accountId,
            InternalPeriodicPaymentExecutionRequestDTO requestDTO
    );

    /**
     * Simulate the cancellation of an existing periodic internal payment (triggers OTP).
     */
    Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(
            Long accountId,
            InternalPeriodicPaymentCancelSimulationRequestDTO requestDTO
    );

    /**
     * Execute (finalize) the cancellation of a periodic internal payment,
     * consuming the OTP.
     */
    Mono<PaymentOperationResponseDTO> executeCancelPeriodic(
            Long accountId,
            InternalPeriodicPaymentCancelExecutionRequestDTO requestDTO
    );
}