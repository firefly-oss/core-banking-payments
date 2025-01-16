package com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Norma34PeriodicPaymentCancelExecutionRequestDTO {
    private Long periodicPaymentReference;
    private String otpCode;
}
