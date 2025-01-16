package com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentPeriodicScheduleDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * DTO to finalize (execute) the creation of a periodic internal payment,
 * consuming the OTP.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InternalPeriodicPaymentExecutionRequestDTO extends InternalPaymentExecutionRequestDTO {
    private PaymentPeriodicScheduleDTO schedule;
}