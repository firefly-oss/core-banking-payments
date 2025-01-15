package com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1;

import com.catalis.common.core.filters.FilterableId;
import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
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
    private BigDecimal feeAmount;
    private String feeCurrencyCode;
}
