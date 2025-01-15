package com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO for canceling an internal transfer operation
 * (either a scheduled or pending internal transaction).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalCancelDTO {

    /**
     * Reference or identifier of the internal transfer (or schedule) to cancel.
     */
    private String internalReference;

    /**
     * The date/time when the cancellation request is made.
     */
    private LocalDateTime cancelRequestDateTime;

    /**
     * Reason or note regarding the cancellation.
     */
    private String cancellationReason;
}
