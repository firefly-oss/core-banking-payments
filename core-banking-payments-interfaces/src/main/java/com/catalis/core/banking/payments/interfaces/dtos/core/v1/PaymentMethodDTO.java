package com.catalis.core.banking.payments.interfaces.dtos.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.banking.payments.interfaces.enums.payment.v1.PaymentPriorityEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentMethodId;

    private String methodName;    // e.g. "SEPA_SCT", "SWIFT", "SEPA_INST"
    private String description;
    private Boolean activeFlag;
    private Integer processingTimeHours;
    private java.math.BigDecimal minAmount;
    private java.math.BigDecimal maxAmount;
    private String[] supportedCurrencies;
    private Boolean requiresIntermediaryBank;
    private Boolean requiresRegulatoryReporting;
    private Boolean requiresPurposeCode;
    private PaymentPriorityEnum defaultPriority;
}
