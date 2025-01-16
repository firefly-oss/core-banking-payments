package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct;

import lombok.*;

/**
 * DTO to simulate cancellation of an existing periodic SEPA SCT payment.
 * Triggers OTP if required. No OTP code here.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaSctPeriodicPaymentCancelSimulationRequestDTO {

    /**
     * A reference or ID that identifies which periodic instruction to cancel.
     */
    private Long periodicPaymentReference;
}