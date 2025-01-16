package com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentPeriodicScheduleDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SwiftPeriodicPaymentExecutionRequestDTO extends SwiftPaymentExecutionRequestDTO {
    private PaymentPeriodicScheduleDTO schedule;
}