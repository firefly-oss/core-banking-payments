package com.catalis.core.banking.payments.interfaces.dtos.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.banking.payments.interfaces.enums.fee.v1.FeeTypeEnum;
import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFeeDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentFeeId;

    @FilterableId
    private Long paymentOrderId;
    private String feeType;
    private FeeTypeEnum feeTypeEnum;
    private BigDecimal feeAmount;
    private String feeCurrencyCode;
    private String feeDescription;
    private String feeCalculationMethod;
    private BigDecimal feeRate;
    private BigDecimal feeTaxAmount;
    private BigDecimal feeTaxRate;
    private String feeTaxType;
    private Boolean feeWaived;
}
