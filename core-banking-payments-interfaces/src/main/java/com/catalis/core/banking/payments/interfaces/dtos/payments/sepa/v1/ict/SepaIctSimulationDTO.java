package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for simulating a SEPA Instant Credit Transfer (ICT).
 * Checks fees, balance availability, etc.
 * Optionally triggers an OTP request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaIctSimulationDTO {

    private String iban;        // IBAN for the instant transfer
    private String bic;         // Bank Identifier Code
    private BigDecimal amount;
    private String currency;    // e.g. "EUR"
    private String reference;

    /**
     * Proposed date/time for the instant transfer (usually immediate, but you can store a timestamp).
     */
    private LocalDateTime intendedDateTime;

    /**
     * Whether to trigger an OTP request upon successful simulation.
     */
    private boolean triggerOtp;
}