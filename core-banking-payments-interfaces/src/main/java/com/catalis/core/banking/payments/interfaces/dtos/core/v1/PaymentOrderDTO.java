package com.catalis.core.banking.payments.interfaces.dtos.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.banking.payments.interfaces.enums.beneficiary.v1.BeneficiaryTypeEnum;
import com.catalis.core.banking.payments.interfaces.enums.payment.v1.PaymentCategoryEnum;
import com.catalis.core.banking.payments.interfaces.enums.payment.v1.PaymentChannelEnum;
import com.catalis.core.banking.payments.interfaces.enums.payment.v1.PaymentPriorityEnum;
import com.catalis.core.banking.payments.interfaces.enums.payment.v1.PaymentPurposeEnum;
import com.catalis.core.banking.payments.interfaces.enums.payment.v1.PaymentStatusEnum;
import com.catalis.core.banking.payments.interfaces.enums.payment.v1.PaymentTypeEnum;
import com.catalis.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @FilterableId
    private Long paymentBeneficiaryId;

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

    private String referenceNumber;
    private String endToEndId;
    private PaymentPriorityEnum priority;
    private PaymentPurposeEnum purpose;
    private String purposeCode;
    private PaymentChannelEnum channel;
    private PaymentCategoryEnum category;
    private BigDecimal exchangeRate;
    private String chargeBearer;
    private BeneficiaryTypeEnum beneficiaryType;
    private String beneficiaryBankName;
    private String beneficiaryBankAddress;
    private String beneficiaryBankCountryCode;
    private String intermediaryBankName;
    private String intermediaryBankBic;
    private String intermediaryBankAccount;
    private String regulatoryReporting;
    private String taxInformation;
    private String batchId;
    private LocalDate valueDate;
    private LocalDate requestedExecutionDate;
}
