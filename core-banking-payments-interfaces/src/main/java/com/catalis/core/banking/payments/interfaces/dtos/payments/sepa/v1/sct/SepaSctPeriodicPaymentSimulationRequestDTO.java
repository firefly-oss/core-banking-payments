package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentPeriodicScheduleDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SepaSctPeriodicPaymentSimulationRequestDTO extends SepaSctPaymentSimulationRequestDTO {
    private PaymentPeriodicScheduleDTO schedule;
}