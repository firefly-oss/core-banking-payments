package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * DTO for scheduling recurring or delayed SEPA SCT payments.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaSctScheduleDTO {

    private String iban;
    private String bic;
    private BigDecimal amount;
    private String currency;
    private String reference;

    /**
     * Date/time to start the scheduled transfers.
     */
    private LocalDateTime startDateTime;

    /**
     * Frequency (e.g., every 1 month). Use java.time.Period or Duration.
     */
    private Period frequency;

    /**
     * Number of occurrences for this schedule.
     * Alternatively, you could have an endDateTime or both.
     */
    private int totalOccurrences;
}