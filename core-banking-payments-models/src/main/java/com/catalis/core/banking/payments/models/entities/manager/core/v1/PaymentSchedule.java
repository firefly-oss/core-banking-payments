package com.catalis.core.banking.payments.models.entities.manager.core.v1;

import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentFrequencyEnum;
import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentScheduleStatusEnum;
import com.catalis.core.banking.payments.models.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_schedule")
public class PaymentSchedule extends BaseEntity {

    @Id
    @Column("payment_schedule_id")
    private Long paymentScheduleId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("scheduled_date")
    private LocalDateTime scheduledDate;

    @Column("amount")
    private BigDecimal amount;

    @Column("frequency")
    private PaymentFrequencyEnum frequency;

    @Column("schedule_status")
    private PaymentScheduleStatusEnum scheduleStatus;
}