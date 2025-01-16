package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct;

import lombok.*;

/**
 * DTO to finalize (execute) the cancellation of a periodic SEPA SCT payment,
 * consuming the OTP code.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaSctPeriodicPaymentCancelExecutionRequestDTO {

    private Long periodicPaymentReference;

    /**
     * OTP code for final authorization of the cancellation.
     */
    private String otpCode;
}