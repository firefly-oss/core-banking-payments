package com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for executing a Payroll Norma 34 operation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollNorma34ExecutionDTO {

    /**
     * The unique payroll batch identifier (as referenced in the simulation).
     */
    private String payrollBatchId;

    /**
     * The total amount being disbursed in this payroll.
     */
    private BigDecimal totalAmount;

    private String currency;
    private String reference;

    /**
     * An OTP for final authorization (if required).
     */
    private String otp;

    /**
     * The actual date/time of the execution.
     */
    private LocalDateTime executionDateTime;
}
