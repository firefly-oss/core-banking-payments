package com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO for canceling a payroll operation (pending or scheduled) under Norma 34.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollNorma34CancelDTO {

    /**
     * Reference to the specific payroll operation or schedule to cancel.
     */
    private String payrollReference;

    /**
     * Date/time when the cancellation request is triggered.
     */
    private LocalDateTime cancelRequestDateTime;

    /**
     * Reason or notes for cancellation.
     */
    private String cancellationReason;
}
