package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * DTO for scheduling a recurring or delayed SEPA Direct Debit.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaDirectDebitScheduleDTO {

    private String iban;
    private String bic;
    private BigDecimal amount;
    private String currency;
    private String reference;
    private String mandateId;

    private LocalDateTime startDateTime;
    private Period frequency;
    private int totalOccurrences;
}

