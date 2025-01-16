package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectDebitPeriodicPaymentCancelExecutionRequestDTO {
    private Long periodicPaymentReference;
    private String otpCode;
}