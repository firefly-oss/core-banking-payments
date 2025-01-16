package com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1;

import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Execution, cancel, schedule, or periodic setup for a Norma 34 payroll batch.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Norma34PaymentExecutionRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentMethodTypeEnum methodType = PaymentMethodTypeEnum.NORMA_34;

    private String operationReference;

    private BigDecimal totalAmount;
    private String currencyFrom;
    private String currencyTo;

    private LocalDateTime requestedExecutionDate;

    private List<Norma34PayrollLineDTO> payrollLines;

    private String payrollConcept;

    private String otpCode;
}