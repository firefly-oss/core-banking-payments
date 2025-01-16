package com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Used to execute (or finalize) an internal payment.
 * Includes OTP code if required by your SCA policy.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InternalPaymentExecutionRequestDTO {

    /**
     * The internal account ID (destination).
     */
    private Long destinationAccountId;

    /**
     * The amount to transfer.
     */
    private BigDecimal amount;

    /**
     * Currency for the transfer.
     */
    private String currency;

    /**
     * A note or description (e.g. "Rent payment").
     */
    private String concept;

    /**
     * Requested date/time if scheduling or executing a future transaction.
     */
    private LocalDateTime requestedExecutionDate;

    /**
     * The OTP code for final authorization (if needed).
     */
    private String otpCode;
}