package com.firefly.core.banking.payments.models.entities.core.v1;

import com.firefly.core.banking.payments.interfaces.enums.beneficiary.v1.BeneficiaryTypeEnum;
import com.firefly.core.banking.payments.interfaces.enums.payment.v1.*;
import com.firefly.core.banking.payments.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_order")
public class PaymentOrder extends BaseEntity {

    @Id
    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("payer_account_id")
    private Long payerAccountId;

    @Column("payment_method_id")
    private Long paymentMethodId;

    @Column("beneficiary_name")
    private String beneficiaryName;

    @Column("beneficiary_account_number")
    private String beneficiaryAccountNumber;

    @Column("beneficiary_iban")
    private String beneficiaryIban;

    @Column("beneficiary_bic")
    private String beneficiaryBic;

    @Column("beneficiary_address")
    private String beneficiaryAddress;

    @Column("beneficiary_country_code")
    private String beneficiaryCountryCode;

    @Column("aba_routing_number")
    private String abaRoutingNumber;

    @Column("payment_type")
    private PaymentTypeEnum paymentType;

    @Column("order_date")
    private java.time.LocalDateTime orderDate;

    @Column("status")
    private PaymentStatusEnum status;

    @Column("amount")
    private java.math.BigDecimal amount;

    @Column("currency_code")
    private String currencyCode;

    @Column("swift_bic_code")
    private String swiftBicCode;

    @Column("remittance_information")
    private String remittanceInformation;

    @Column("document_id")
    private Long documentId;

    @Column("payment_beneficiary_id")
    private Long paymentBeneficiaryId;

    @Column("reference_number")
    private String referenceNumber;

    @Column("end_to_end_id")
    private String endToEndId;

    @Column("priority")
    private PaymentPriorityEnum priority;

    @Column("purpose")
    private PaymentPurposeEnum purpose;

    @Column("purpose_code")
    private String purposeCode;

    @Column("channel")
    private PaymentChannelEnum channel;

    @Column("category")
    private PaymentCategoryEnum category;

    @Column("exchange_rate")
    private java.math.BigDecimal exchangeRate;

    @Column("charge_bearer")
    private String chargeBearer;

    @Column("beneficiary_type")
    private BeneficiaryTypeEnum beneficiaryType;

    @Column("beneficiary_bank_name")
    private String beneficiaryBankName;

    @Column("beneficiary_bank_address")
    private String beneficiaryBankAddress;

    @Column("beneficiary_bank_country_code")
    private String beneficiaryBankCountryCode;

    @Column("intermediary_bank_name")
    private String intermediaryBankName;

    @Column("intermediary_bank_bic")
    private String intermediaryBankBic;

    @Column("intermediary_bank_account")
    private String intermediaryBankAccount;

    @Column("regulatory_reporting")
    private String regulatoryReporting;

    @Column("tax_information")
    private String taxInformation;

    @Column("batch_id")
    private String batchId;

    @Column("value_date")
    private java.time.LocalDate valueDate;

    @Column("requested_execution_date")
    private java.time.LocalDate requestedExecutionDate;
}