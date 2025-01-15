package com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for executing an internal (intra-bank) transfer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalExecutionDTO {

    private String sourceAccountId;
    private String destinationAccountId;
    private BigDecimal amount;
    private String currency;
    private String reference;

    /**
     * The OTP needed for final authorization.
     */
    private String otp;

    /**
     * The actual execution date/time for this transfer.
     */
    private LocalDateTime executionDateTime;
}

