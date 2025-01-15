package com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollNorma34SimulationDTO {

    /**
     * The unique identifier for the payroll batch you plan to simulate.
     * Could be an internal reference or external file reference.
     */
    private String payrollBatchId;

    /**
     * The total amount of the payroll to simulate (sum of employees' salaries, for instance).
     */
    private BigDecimal totalAmount;

    /**
     * The currency, e.g., "EUR".
     */
    private String currency;

    /**
     * A reference or description for this payroll operation.
     */
    private String reference;

    /**
     * Proposed execution date/time for the payroll batch.
     */
    private LocalDateTime intendedDateTime;

    /**
     * Whether to trigger an OTP request after simulation is complete.
     */
    private boolean triggerOtp;
}
