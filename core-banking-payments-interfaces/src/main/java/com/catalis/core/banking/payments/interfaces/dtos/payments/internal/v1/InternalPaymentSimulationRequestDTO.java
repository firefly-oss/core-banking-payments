package com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Used to simulate an internal payment.
 * No OTP code here because simulation triggers the OTP.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InternalPaymentSimulationRequestDTO {

    /**
     * The internal account ID to which we send money (destination).
     */
    private Long destinationAccountId;

    /**
     * The amount to transfer.
     */
    private BigDecimal amount;

    /**
     * Currency (typically "EUR" in Spain).
     */
    private String currency;

    /**
     * A note or description for the payment (e.g. "Rent payment").
     */
    private String concept;

    /**
     * If scheduling for the future, the date/time to execute.
     */
    private LocalDateTime requestedExecutionDate;

}