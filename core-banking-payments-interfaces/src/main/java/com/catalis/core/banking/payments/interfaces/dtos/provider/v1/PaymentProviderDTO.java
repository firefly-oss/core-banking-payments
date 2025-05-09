package com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.banking.payments.interfaces.enums.manager.provider.v1.ProviderStatusEnum;
import com.catalis.core.utils.annotations.FilterableId;
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
}
