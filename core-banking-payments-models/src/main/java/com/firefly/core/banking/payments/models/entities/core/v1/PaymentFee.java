package com.firefly.core.banking.payments.models.entities.core.v1;

import com.firefly.core.banking.payments.interfaces.enums.fee.v1.FeeTypeEnum;
import com.firefly.core.banking.payments.models.entities.BaseEntity;
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
@Table("payment_fee")
public class PaymentFee extends BaseEntity {
    @Id
    @Column("payment_fee_id")
    private Long paymentFeeId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("fee_type")
    private String feeType;

    @Column("fee_type_enum")
    private FeeTypeEnum feeTypeEnum;

    @Column("fee_amount")
    private BigDecimal feeAmount;

    @Column("fee_currency_code")
    private String feeCurrencyCode;

    @Column("fee_description")
    private String feeDescription;

    @Column("fee_calculation_method")
    private String feeCalculationMethod;

    @Column("fee_rate")
    private BigDecimal feeRate;

    @Column("fee_tax_amount")
    private BigDecimal feeTaxAmount;

    @Column("fee_tax_rate")
    private BigDecimal feeTaxRate;

    @Column("fee_tax_type")
    private String feeTaxType;

    @Column("fee_waived")
    private Boolean feeWaived;
}
