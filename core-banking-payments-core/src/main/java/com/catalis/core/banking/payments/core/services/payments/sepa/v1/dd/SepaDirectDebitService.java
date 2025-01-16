package com.catalis.core.banking.payments.core.services.payments.sepa.v1.dd;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.*;
import reactor.core.publisher.Mono;

/**
 * Service interface for handling SEPA Direct Debit operations:
 * Simulation, Execution, Scheduling, and Deletion (simulate & execute).
 */
public interface SepaDirectDebitService {

    /**
     * Simulates a SEPA Direct Debit payment.
     * Typically triggers an OTP if SCA is required.
     *
     * @param accountId      The debtor's account ID.
     * @param simulationDTO  The details of the payment (operationType, amount, etc.).
     * @return A Mono with PaymentOperationResponseDTO (e.g. status = "PENDING_OTP").
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(
            Long accountId,
            DirectDebitPaymentSimulationRequestDTO simulationDTO
    );

    /**
     * Executes a SEPA Direct Debit payment.
     * Consumes the OTP if required.
     *
     * @param accountId     The debtor's account ID.
     * @param executionDTO  The details of the payment (including OTP if needed).
     * @return A Mono with PaymentOperationResponseDTO (e.g. success/failure).
     */
    Mono<PaymentOperationResponseDTO> executePayment(
            Long accountId,
            DirectDebitPaymentExecutionRequestDTO executionDTO
    );


    /**
     * Simulates the deletion (cancellation) of a SEPA Direct Debit.
     * Typically triggers an OTP for confirmation.
     *
     * @param accountId           The debtor's account ID.
     * @param deleteSimulationDTO The details of the payment to be deleted.
     * @return A Mono with PaymentOperationResponseDTO (likely "PENDING_OTP").
     */
    Mono<PaymentOperationResponseDTO> simulateDeletion(
            Long accountId,
            DirectDebitPaymentSimulationRequestDTO deleteSimulationDTO
    );

    /**
     * Executes the deletion (cancellation) of a SEPA Direct Debit.
     * Consumes the OTP if required.
     *
     * @param accountId          The debtor's account ID.
     * @param deleteExecutionDTO The details (including OTP) to finalize deletion.
     * @return A Mono with PaymentOperationResponseDTO (success/failure).
     */
    Mono<PaymentOperationResponseDTO> executeDeletion(
            Long accountId,
            DirectDebitPaymentExecutionRequestDTO deleteExecutionDTO
    );

    // PERIODIC creation
    Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(
            Long accountId,
            DirectDebitPeriodicPaymentSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executePeriodicPayment(
            Long accountId,
            DirectDebitPeriodicPaymentExecutionRequestDTO requestDTO
    );

    // PERIODIC cancellation
    Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(
            Long accountId,
            DirectDebitPeriodicPaymentCancelSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executeCancelPeriodic(
            Long accountId,
            DirectDebitPeriodicPaymentCancelExecutionRequestDTO requestDTO
    );
}

