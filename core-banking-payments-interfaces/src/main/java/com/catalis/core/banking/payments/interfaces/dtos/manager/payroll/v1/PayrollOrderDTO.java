package com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1;

import com.catalis.common.core.filters.FilterableId;
import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.banking.payments.interfaces.enums.manager.payroll.v1.PayrollStatusEnum;
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
