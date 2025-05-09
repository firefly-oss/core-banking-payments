package com.catalis.core.banking.payments.interfaces.dtos.exchange.v1;

import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
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
public class PaymentExchangeRateDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentExchangeRateId;

    @FilterableId
    private Long paymentOrderId;

    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal rate;
    private LocalDateTime rateDate;
    private String rateProvider;
    private String rateType; // 'SPOT', 'FORWARD', 'FIXED'
    private BigDecimal markupPercentage;
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
}
