package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO for canceling a SEPA Direct Debit operation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaDirectDebitCancelDTO {

    /**
     * Reference or ID for the direct debit operation
     * or the mandate reference if you are canceling a scheduled debit.
     */
    private String directDebitReference;

    /**
     * Date/time when the cancellation request is triggered.
     */
    private LocalDateTime cancelRequestDateTime;

    /**
     * Reason for the cancellation.
     */
    private String cancellationReason;
}
