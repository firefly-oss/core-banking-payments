package com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for simulating an internal (intra-bank) transfer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalSimulationDTO {

    /**
     * Source account identifier within the same bank.
     */
    private String sourceAccountId;

    /**
     * Destination account identifier within the same bank.
     */
    private String destinationAccountId;

    /**
     * Amount to transfer.
     */
    private BigDecimal amount;

    /**
     * Currency, e.g. "EUR", "USD", etc. (assuming your bank supports multiple currencies).
     */
    private String currency;

    /**
     * A payment or transfer reference for internal use.
     */
    private String reference;

    /**
     * Proposed date/time for the internal transfer.
     */
    private LocalDateTime intendedDateTime;

    /**
     * Whether to trigger an OTP request after successful simulation.
     */
    private boolean triggerOtp;
}