package com.catalis.core.banking.payments.models.entities.manager.core.v1;

import com.catalis.core.banking.payments.models.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_fee")
public class PaymentFee extends BaseEntity {
    @Id
    @Column("payment_fee_id")
    private Long paymentFeeId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("fee_type")
    private String feeType;

    @Column("fee_amount")
    private BigDecimal feeAmount;

    @Column("fee_currency_code")
    private String feeCurrencyCode;
}
