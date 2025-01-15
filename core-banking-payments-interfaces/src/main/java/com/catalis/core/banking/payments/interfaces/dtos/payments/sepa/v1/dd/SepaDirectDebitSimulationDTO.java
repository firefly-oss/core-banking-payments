package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for simulating a SEPA Direct Debit.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaDirectDebitSimulationDTO {

    private String iban;
    private String bic;
    private BigDecimal amount;
    private String currency;
    private String reference;

    /**
     * SEPA Direct Debit Mandate ID.
     */
    private String mandateId;

    private LocalDateTime intendedDebitDateTime;
    private boolean triggerOtp;
}

