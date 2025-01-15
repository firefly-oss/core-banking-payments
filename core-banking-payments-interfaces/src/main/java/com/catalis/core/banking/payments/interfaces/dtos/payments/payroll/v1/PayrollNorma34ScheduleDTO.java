package com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * DTO for scheduling repeated or delayed payroll operations under Norma 34.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollNorma34ScheduleDTO {

    private String payrollBatchId;
    private BigDecimal totalAmount;
    private String currency;
    private String reference;

    /**
     * Date/time to start scheduling the payroll batch.
     */
    private LocalDateTime startDateTime;

    /**
     * Frequency of the payroll (weekly, monthly, etc.).
     * Represented here by Period (e.g., P1M for monthly).
     */
    private Period frequency;

    /**
     * Number of payroll occurrences to schedule, e.g., 12 times.
     */
    private int totalOccurrences;
}