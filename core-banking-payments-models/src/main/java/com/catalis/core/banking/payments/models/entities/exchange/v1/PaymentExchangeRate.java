package com.catalis.core.banking.payments.models.entities.exchange.v1;

import com.catalis.core.banking.payments.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_exchange_rate")
public class PaymentExchangeRate extends BaseEntity {

    @Id
    @Column("payment_exchange_rate_id")
    private Long paymentExchangeRateId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("source_currency")
    private String sourceCurrency;

    @Column("target_currency")
    private String targetCurrency;

    @Column("rate")
    private BigDecimal rate;

    @Column("rate_date")
    private LocalDateTime rateDate;

    @Column("rate_provider")
    private String rateProvider;

    @Column("rate_type")
    private String rateType; // 'SPOT', 'FORWARD', 'FIXED'

    @Column("markup_percentage")
    private BigDecimal markupPercentage;

    @Column("original_amount")
    private BigDecimal originalAmount;

    @Column("converted_amount")
    private BigDecimal convertedAmount;
}
