package com.catalis.core.banking.payments.core.services.payments.sepa.v1.ict;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict.*;
import reactor.core.publisher.Mono;

/**
 * Service interface for handling SEPA Instant Credit Transfers (ICT),
 * including simulation, execution, and deletion (simulate & execute).
 */
public interface SepaIctService {

    /**
     * Simulates a SEPA ICT payment. Typically triggers OTP if required.
     *
     * @param accountId      The account ID (debtor).
     * @param simulationDTO  Details about the payment to simulate.
     * @return A Mono of PaymentOperationResponseDTO,
     *         likely with status = "PENDING_OTP" or similar.
     */
    Mono<PaymentOperationResponseDTO> simulatePayment(
            Long accountId,
            SepaIctPaymentSimulationRequestDTO simulationDTO
    );

    /**
     * Executes a SEPA ICT payment. Consumes OTP if needed.
     *
     * @param accountId     The account ID (debtor).
     * @param executionDTO  Details about the payment, including OTP if needed.
     * @return A Mono of PaymentOperationResponseDTO
     *         with success/failure details.
     */
    Mono<PaymentOperationResponseDTO> executePayment(
            Long accountId,
            SepaIctPaymentExecutionRequestDTO executionDTO
    );

    /**
     * Simulates the deletion (cancellation) of a SEPA ICT payment.
     * Typically triggers OTP for user confirmation.
     *
     * @param accountId           The account ID (debtor).
     * @param deleteSimulationDTO Details identifying the payment to be deleted.
     * @return A Mono of PaymentOperationResponseDTO (likely "PENDING_OTP").
     */
    Mono<PaymentOperationResponseDTO> simulateDeletion(
            Long accountId,
            SepaIctPaymentSimulationRequestDTO deleteSimulationDTO
    );

    /**
     * Executes the deletion (cancellation) of a SEPA ICT payment.
     * Consumes OTP.
     *
     * @param accountId          The account ID (debtor).
     * @param deleteExecutionDTO Details (including OTP) to finalize deletion.
     * @return A Mono of PaymentOperationResponseDTO
     *         with final status (success/failure).
     */
    Mono<PaymentOperationResponseDTO> executeDeletion(
            Long accountId,
            SepaIctPaymentExecutionRequestDTO deleteExecutionDTO
    );

    // PERIODIC creation
    Mono<PaymentOperationResponseDTO> simulatePeriodicPayment(
            Long accountId,
            SepaIctPeriodicPaymentSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executePeriodicPayment(
            Long accountId,
            SepaIctPeriodicPaymentExecutionRequestDTO requestDTO
    );

    // PERIODIC cancellation
    Mono<PaymentOperationResponseDTO> simulateCancelPeriodic(
            Long accountId,
            SepaIctPeriodicPaymentCancelSimulationRequestDTO requestDTO
    );

    Mono<PaymentOperationResponseDTO> executeCancelPeriodic(
            Long accountId,
            SepaIctPeriodicPaymentCancelExecutionRequestDTO requestDTO
    );
}
