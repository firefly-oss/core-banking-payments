package com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1;

import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentFrequencyEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentPeriodicScheduleDTO {

    /**
     * The frequency at which this payment recurs.
     * E.g. MONTHLY, WEEKLY, ANNUAL, etc.
     */
    private PaymentFrequencyEnum frequency;

    /**
     * The date on which to start the periodic payments.
     * E.g. 2025-03-01
     */
    private LocalDate startDate;

    /**
     * The date on which to end the periodic payments (inclusive or exclusive).
     * If null, it may represent an indefinite recurrence or
     * until the user manually cancels.
     */
    private LocalDate endDate;

    /**
     * The day of the month on which to execute the payment
     * if monthly or an appropriate day for other frequencies.
     * <p>
     * For example, "5" means the 5th of each month.
     * For WEEKLY, you might interpret "1" = Monday, "2" = Tuesday, etc.
     * (Implementation detail depends on your system).
     */
    private Integer dayOfExecution;
}
