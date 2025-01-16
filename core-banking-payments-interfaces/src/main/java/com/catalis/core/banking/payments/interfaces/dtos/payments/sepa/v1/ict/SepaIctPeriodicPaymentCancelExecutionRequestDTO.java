package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaIctPeriodicPaymentCancelExecutionRequestDTO {
    private Long periodicPaymentReference;
    private String otpCode;
}