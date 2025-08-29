package com.firefly.core.banking.payments.models.entities.core.v1;

import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentFrequencyEnum;
import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentScheduleStatusEnum;
import com.firefly.core.banking.payments.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Column("end_date")
    private LocalDate endDate;

    @Column("max_executions")
    private Integer maxExecutions;

    @Column("current_execution_count")
    private Integer currentExecutionCount;

    @Column("next_execution_date")
    private LocalDate nextExecutionDate;

    @Column("day_of_month")
    private Integer dayOfMonth;

    @Column("day_of_week")
    private Integer dayOfWeek;

    @Column("week_of_month")
    private Integer weekOfMonth;

    @Column("month_of_year")
    private Integer monthOfYear;

    @Column("schedule_notes")
    private String scheduleNotes;
}