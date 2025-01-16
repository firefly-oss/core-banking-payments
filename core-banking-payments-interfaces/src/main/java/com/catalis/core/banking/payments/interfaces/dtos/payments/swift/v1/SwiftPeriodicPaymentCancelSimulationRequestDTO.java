package com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwiftPeriodicPaymentCancelSimulationRequestDTO {
    private Long periodicPaymentReference;
}

