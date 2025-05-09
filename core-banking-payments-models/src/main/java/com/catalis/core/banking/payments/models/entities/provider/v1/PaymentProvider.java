package com.catalis.core.banking.payments.models.entities.provider.v1;

import com.catalis.core.banking.payments.interfaces.enums.provider.v1.ProviderStatusEnum;
import com.catalis.core.banking.payments.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_provider")
public class PaymentProvider extends BaseEntity {

    @Id
    @Column("payment_provider_id")
    private Long paymentProviderId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("provider_name")
    private String providerName;

    @Column("external_reference")
    private String externalReference;

    @Column("status")
    private ProviderStatusEnum status;

    @Column("provider_type")
    private String providerType;

    @Column("provider_url")
    private String providerUrl;

    @Column("provider_api_key")
    private String providerApiKey;

    @Column("provider_username")
    private String providerUsername;

    @Column("provider_account_id")
    private String providerAccountId;

    @Column("provider_fee")
    private BigDecimal providerFee;

    @Column("provider_fee_currency_code")
    private String providerFeeCurrencyCode;

    @Column("provider_response_code")
    private String providerResponseCode;

    @Column("provider_response_message")
    private String providerResponseMessage;

    @Column("provider_transaction_id")
    private String providerTransactionId;
}
