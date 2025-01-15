package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for executing a SEPA Direct Debit.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaDirectDebitExecutionDTO {

    private String iban;
    private String bic;
    private BigDecimal amount;
    private String currency;
    private String reference;
    private String mandateId;

    /**
     * OTP for final authorization, if applicable.
     */
    private String otp;

    private LocalDateTime executionDateTime;
}