package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for executing a SEPA SCT.
 * Requires an OTP if your flow mandates second-factor authentication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaSctExecutionDTO {

    private String iban;
    private String bic;
    private BigDecimal amount;
    private String currency;
    private String reference;

    /**
     * OTP required for final authorization.
     */
    private String otp;

    /**
     * Date/time the execution is actually taking place.
     */
    private LocalDateTime executionDateTime;
}
