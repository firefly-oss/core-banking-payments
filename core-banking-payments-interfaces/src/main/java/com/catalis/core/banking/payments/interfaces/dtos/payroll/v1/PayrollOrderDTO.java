package com.catalis.core.banking.payments.interfaces.dtos.payroll.v1;

import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.banking.payments.interfaces.enums.payroll.v1.PayrollStatusEnum;
import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollOrderDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long payrollOrderId;

    @FilterableId
    private Long paymentOrderId;

    private String payrollReference;
    private LocalDateTime payrollDate;
    private BigDecimal totalAmount;

    // e.g. "Initiated", "Processed", "Failed"
    private PayrollStatusEnum payrollStatus;
}
