package com.firefly.core.banking.payments.models.entities.core.v1;

import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentPriorityEnum;
import com.firefly.core.banking.payments.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_method")
public class PaymentMethod extends BaseEntity {
    @Id
    @Column("payment_method_id")
    private UUID paymentMethodId;

    @Column("method_name")
    private String methodName;

    @Column("description")
    private String description;

    @Column("active_flag")
    private Boolean activeFlag;

    @Column("processing_time_hours")
    private Integer processingTimeHours;

    @Column("min_amount")
    private BigDecimal minAmount;

    @Column("max_amount")
    private BigDecimal maxAmount;

    @Column("supported_currencies")
    private String[] supportedCurrencies;

    @Column("requires_intermediary_bank")
    private Boolean requiresIntermediaryBank;

    @Column("requires_regulatory_reporting")
    private Boolean requiresRegulatoryReporting;

    @Column("requires_purpose_code")
    private Boolean requiresPurposeCode;

    @Column("default_priority")
    private PaymentPriorityEnum defaultPriority;
}
