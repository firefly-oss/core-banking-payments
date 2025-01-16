package com.catalis.core.banking.payments.core.services.payments.sepa.v1.sct;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.*;
import reactor.core.publisher.Mono;

/**
 * Service interface for handling SEPA Credit Transfer (SCT) operations:
 * simulation, execution, and deletion (two-step OTP approach).
 */
public interface SepaSctService {

    /**
     * Simulates a SEPA SCT payment (could be immediate or scheduled/periodic).
     * Typically triggers an OTP if SCA is required.
     *
     * @param accountId      The debtor's account ID (path variable).
     * @param simulationDTO  Details of the SCT payment to simulate.
     * @return Mono of PaymentOperationResponseDTO, e.g. with status = "PENDING_OTP".
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(
            Long accountId,
            SepaSctPaymentSimulationRequestDTO simulationDTO
    );

    /**
     * Executes a SEPA SCT payment, consuming OTP if required.
     *
     * @param accountId     The debtor's account ID (path variable).
     * @param executionDTO  Details of the SCT payment (includes OTP).
     * @return Mono of PaymentOperationResponseDTO indicating success/failure.
     */
    Mono<PaymentOperationResponseDTO> executePayment(
            Long accountId,
            SepaSctPaymentExecutionRequestDTO executionDTO
    );

    /**
     * Simulates deletion of a SEPA SCT payment (triggers OTP).
     *
     * @param accountId          The debtor's account ID.
     * @param deleteSimulationDTO Identifies the payment to be deleted.
     * @return Mono of PaymentOperationResponseDTO, likely status = "PENDING_OTP".
     */
    Mono<PaymentOperationResponseDTO> simulateDeletion(
            Long accountId,
            SepaSctPaymentSimulationRequestDTO deleteSimulationDTO
    );

    /**
     * Executes the deletion of a SEPA SCT payment, consuming the OTP.
     *
     * @param accountId         The debtor's account ID.
     * @param deleteExecutionDTO Details (including OTP) to finalize the deletion.
     * @return Mono of PaymentOperationResponseDTO indicating success/failure.
     */
    Mono<PaymentOperationResponseDTO> executeDeletion(
            Long accountId,
            SepaSctPaymentExecutionRequestDTO deleteExecutionDTO
    );

    // PERIODIC creation
    Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(
            Long accountId,
            SepaSctPeriodicPaymentSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executePeriodicPayment(
            Long accountId,
            SepaSctPeriodicPaymentExecutionRequestDTO requestDTO
    );

    // PERIODIC cancellation
    Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(
            Long accountId,
            SepaSctPeriodicPaymentCancelSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executeCancelPeriodic(
            Long accountId,
            SepaSctPeriodicPaymentCancelExecutionRequestDTO requestDTO
    );
}
