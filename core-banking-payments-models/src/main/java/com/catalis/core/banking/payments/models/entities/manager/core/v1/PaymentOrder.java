package com.catalis.core.banking.payments.models.entities.manager.core.v1;

import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentStatusEnum;
import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentTypeEnum;
import com.catalis.core.banking.payments.models.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

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
}