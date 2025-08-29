package com.firefly.core.banking.payments.interfaces.dtos.provider.v1;

import com.firefly.core.banking.payments.interfaces.dtos.BaseDTO;
import com.firefly.core.banking.payments.interfaces.enums.provider.v1.ProviderStatusEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProviderDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentProviderId;

    @FilterableId
    private Long paymentOrderId;

    private String providerName;
    private String externalReference;

    // e.g. "ACTIVE", "INACTIVE", "PENDING", "SUSPENDED"
    private ProviderStatusEnum status;

    private String providerType;
    private String providerUrl;
    private String providerApiKey;
    private String providerUsername;
    private String providerAccountId;
    private java.math.BigDecimal providerFee;
    private String providerFeeCurrencyCode;
    private String providerResponseCode;
    private String providerResponseMessage;
    private String providerTransactionId;
}
