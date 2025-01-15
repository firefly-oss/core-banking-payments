package com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO for canceling a SWIFT operation,
 * which may be a scheduled or pending transfer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwiftCancelDTO {

    /**
     * Reference/ID for the SWIFT operation or schedule to be canceled.
     */
    private String swiftReference;

    /**
     * Date/time the cancellation is requested.
     */
    private LocalDateTime cancelRequestDateTime;

    /**
     * Reason or notes for the cancellation.
     */
    private String cancellationReason;
}

