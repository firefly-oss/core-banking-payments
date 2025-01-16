package com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalPeriodicPaymentCancelSimulationRequestDTO {
    private Long periodicPaymentReference;
}