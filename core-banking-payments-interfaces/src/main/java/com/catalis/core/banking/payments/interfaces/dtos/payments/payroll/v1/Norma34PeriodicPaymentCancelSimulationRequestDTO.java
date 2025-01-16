package com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Norma34PeriodicPaymentCancelSimulationRequestDTO {
    private Long periodicPaymentReference;
}
