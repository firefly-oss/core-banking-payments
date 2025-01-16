package com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1;

import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Norma34PaymentSimulationRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentMethodTypeEnum methodType = PaymentMethodTypeEnum.NORMA_34;

    /**
     * Usually Norma 34 is a batch (payroll) specification,
     * so you might not have a single amount,
     * but for simulation, we might store total or per-line items.
     */
    private BigDecimal totalAmount;

    /**
     * Currency typically EUR in Spain.
     */
    private String currencyFrom;
    private String currencyTo;

    /**
     * Requested execution or payroll date.
     */
    private LocalDateTime requestedExecutionDate;

    /**
     * A list of employees or recipients.
     */
    private List<Norma34PayrollLineDTO> payrollLines;

    /**
     * Additional reference or concept (e.g. "Payroll July 2025").
     */
    private String payrollConcept;

}
