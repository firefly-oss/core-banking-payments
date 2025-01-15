package com.catalis.core.banking.payments.models.entities.manager.provider.v1;

import com.catalis.core.banking.payments.interfaces.enums.manager.provider.v1.ProviderStatusEnum;
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
}
