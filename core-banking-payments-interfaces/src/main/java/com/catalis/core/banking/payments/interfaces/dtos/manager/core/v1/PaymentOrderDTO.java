package com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1;

import com.catalis.common.core.filters.FilterableId;
import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentStatusEnum;
import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentTypeEnum;
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
public class PaymentOrderDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentOrderId;

    @FilterableId
    private Long payerAccountId;

    @FilterableId
    private Long paymentMethodId;

    private String beneficiaryName;
    private String beneficiaryAccountNumber;
    private String beneficiaryIban;
    private String beneficiaryBic;
    private String beneficiaryAddress;
    private String beneficiaryCountryCode;
    private String abaRoutingNumber;

    // e.g. "SEPA_SCT", "SWIFT", etc.
    private PaymentTypeEnum paymentType;

    private LocalDateTime orderDate;

    // e.g. "Initiated", "Completed", "Failed"
    private PaymentStatusEnum status;

    private BigDecimal amount;

    private String currencyCode;
    private String swiftBicCode;
    private String remittanceInformation;

    private Long documentId;
}
