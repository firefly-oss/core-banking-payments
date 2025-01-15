package com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1;

import com.catalis.common.core.filters.FilterableId;
import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentFrequencyEnum;
import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentScheduleStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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
}