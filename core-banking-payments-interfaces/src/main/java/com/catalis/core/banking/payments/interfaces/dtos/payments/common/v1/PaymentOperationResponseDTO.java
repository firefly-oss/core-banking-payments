package com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1;

import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentOperationTypeEnum;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOperationResponseDTO {

    /**
     * The method type (e.g., SEPA_SCT, SEPA_DIRECT_DEBIT, SWIFT, INTERNAL, etc.).
     */
    private PaymentMethodTypeEnum methodType;

    /**
     * The operation type (e.g., SIMULATION, EXECUTION, SCHEDULE, CANCEL).
     */
    private PaymentOperationTypeEnum operationType;

    /**
     * A unique reference or ID for this operation
     * (e.g., an internal transaction reference).
     */
    private String operationReference;

    /**
     * Overall status of the operation
     * (e.g., "SUCCESS", "FAILED", "PENDING_OTP").
     */
    private String status;

    /**
     * A human-readable message or description of the operation result.
     */
    private String message;

    /**
     * Timestamp indicating when this response was generated.
     */
    private LocalDateTime timestamp;

    /**
     * A reference number used to track the OTP session if an OTP was triggered.
     * This value can be used to match the user-provided OTP against the correct operation.
     */
    private String otpReference;

    /**
     * The total fees associated with this payment operation,
     * representing the sum of all applied fees.
     */
    private BigDecimal totalFees;

    /**
     * The date/time on which the amount is expected to be effectively available
     * (a.k.a. settlement or value date).
     */
    private LocalDateTime valueDate;

    /**
     * The date/time on which the transaction is actually posted in the ledger.
     * May or may not match the valueDate depending on processing rules.
     */
    private LocalDateTime postingDate;

    /**
     * Information about the debtor (payer) in this transaction.
     */
    private DebtorDTO debtor;

    /**
     * Information about the creditor (payee) in this transaction.
     */
    private CreditorDTO creditor;

    /**
     * The source currency, if this is a multi-currency transaction
     * (e.g., "EUR", "USD").
     */
    private String currencyFrom;

    /**
     * The target currency, if this is a multi-currency transaction
     * (e.g., "EUR", "USD"). If the transaction is not cross-currency,
     * you can set this to the same value as currencyFrom or leave it null.
     */
    private String currencyTo;

    /**
     * An object storing response data from a Banking-as-a-Service provider.
     * This can contain status codes, messages, raw responses, etc.
     */
    private BaasResponseDTO baasResponse;
}