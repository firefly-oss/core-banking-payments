package com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1;

import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;

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
     */
    private LocalDateTime postingDate;

    /**
     * The source currency, if this is a multi-currency transaction.
     */
    private String currencyFrom;

    /**
     * The target currency, if this is a multi-currency transaction.
     */
    private String currencyTo;

    /**
     * An object storing response data from a Banking-as-a-Service provider.
     */
    private BaasResponseDTO baasResponse;
}