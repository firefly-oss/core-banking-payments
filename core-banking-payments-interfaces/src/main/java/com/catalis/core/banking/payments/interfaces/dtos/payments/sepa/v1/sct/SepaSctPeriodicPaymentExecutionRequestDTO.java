package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentPeriodicScheduleDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * DTO to finalize (execute) the creation of a periodic SEPA SCT payment,
 * consuming the OTP.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SepaSctPeriodicPaymentExecutionRequestDTO extends SepaSctPaymentExecutionRequestDTO {
    private PaymentPeriodicScheduleDTO schedule;
}