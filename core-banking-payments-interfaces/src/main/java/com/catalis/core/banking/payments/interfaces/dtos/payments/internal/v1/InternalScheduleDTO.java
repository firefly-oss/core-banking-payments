package com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * DTO for scheduling a recurring or delayed internal (intra-bank) transfer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalScheduleDTO {

    private String sourceAccountId;
    private String destinationAccountId;
    private BigDecimal amount;
    private String currency;
    private String reference;

    /**
     * Date/time to start the scheduled transfers.
     */
    private LocalDateTime startDateTime;

    /**
     * Frequency (monthly, weekly, etc.). For example, "P1M".
     */
    private Period frequency;

    /**
     * Number of occurrences for this schedule.
     */
    private int totalOccurrences;
}

