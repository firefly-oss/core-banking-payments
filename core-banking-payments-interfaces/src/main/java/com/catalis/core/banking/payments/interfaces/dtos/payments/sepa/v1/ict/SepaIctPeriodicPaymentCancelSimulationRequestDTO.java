package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaIctPeriodicPaymentCancelSimulationRequestDTO {
    private Long periodicPaymentReference;
}
