package com.firefly.core.banking.payments.interfaces.dtos.instruction.v1;

import com.firefly.core.banking.payments.interfaces.dtos.BaseDTO;
import com.firefly.core.banking.payments.interfaces.enums.instruction.v1.InstructionTypeEnum;
import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentPriorityEnum;
import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentStatusEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInstructionDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentInstructionId;

    @FilterableId
    private Long paymentOrderId;

    private String instructionId;
    private InstructionTypeEnum instructionType;   // e.g. "Immediate", "Scheduled"
    private LocalDateTime instructionDate;
    private PaymentStatusEnum instructionStatus; // e.g. "Pending", "Executed", "Cancelled"

    private String externalReference;
    private PaymentPriorityEnum instructionPriority;
    private String instructionNotes;
    private Integer retryCount;
    private LocalDateTime lastRetryDate;
    private String errorCode;
    private String errorDescription;
}
