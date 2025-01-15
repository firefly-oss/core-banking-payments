package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for executing a SEPA Instant Credit Transfer.
 * Requires OTP if your process includes 2FA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaIctExecutionDTO {

    private String iban;
    private String bic;
    private BigDecimal amount;
    private String currency;
    private String reference;

    /**
     * The OTP for authorization, if applicable.
     */
    private String otp;

    /**
     * Actual execution date/time of the transfer.
     */
    private LocalDateTime executionDateTime;
}

