package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO for canceling a SEPA SCT operation,
 * possibly referencing a scheduled or pending payment.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaSctCancelDTO {

    /**
     * Reference to the SEPA SCT operation or schedule that you want to cancel.
     * This might be an internal ID or external reference.
     */
    private String sctReference;

    /**
     * (Optional) The date/time when you're requesting the cancel.
     */
    private LocalDateTime cancelRequestDateTime;

    /**
     * Reason or notes for cancellation.
     */
    private String cancellationReason;
}

