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

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInstructionDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID paymentInstructionId;

    @FilterableId
    @NotNull(message = "Payment order ID is required")
    private UUID paymentOrderId;

    @NotBlank(message = "Instruction ID is required")
    @Size(max = 50, message = "Instruction ID must not exceed 50 characters")
    private String instructionId;

    @NotNull(message = "Instruction type is required")
    private InstructionTypeEnum instructionType;   // e.g. "Immediate", "Scheduled"

    @NotNull(message = "Instruction date is required")
    private LocalDateTime instructionDate;

    @NotNull(message = "Instruction status is required")
    private PaymentStatusEnum instructionStatus; // e.g. "Pending", "Executed", "Cancelled"

    @Size(max = 100, message = "External reference must not exceed 100 characters")
    private String externalReference;

    private PaymentPriorityEnum instructionPriority;

    @Size(max = 500, message = "Instruction notes must not exceed 500 characters")
    private String instructionNotes;

    @Min(value = 0, message = "Retry count must be non-negative")
    @Max(value = 10, message = "Retry count must not exceed 10")
    private Integer retryCount;

    private LocalDateTime lastRetryDate;

    @Size(max = 20, message = "Error code must not exceed 20 characters")
    private String errorCode;

    @Size(max = 500, message = "Error description must not exceed 500 characters")
    private String errorDescription;
}
