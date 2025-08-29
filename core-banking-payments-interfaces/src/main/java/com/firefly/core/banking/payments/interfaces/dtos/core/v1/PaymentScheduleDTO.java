package com.firefly.core.banking.payments.interfaces.dtos.core.v1;

import com.firefly.core.banking.payments.interfaces.dtos.BaseDTO;
import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentFrequencyEnum;
import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentScheduleStatusEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentScheduleDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentScheduleId;

    @FilterableId
    private Long paymentOrderId;

    private LocalDateTime scheduledDate;
    private BigDecimal amount;

    // e.g. "One-time", "Daily", "Weekly", "Monthly", "Yearly"
    private PaymentFrequencyEnum frequency;

    // e.g. "Scheduled", "Completed", "Cancelled"
    private PaymentScheduleStatusEnum scheduleStatus;

    private LocalDate endDate;
    private Integer maxExecutions;
    private Integer currentExecutionCount;
    private LocalDate nextExecutionDate;
    private Integer dayOfMonth;
    private Integer dayOfWeek;
    private Integer weekOfMonth;
    private Integer monthOfYear;
    private String scheduleNotes;
}