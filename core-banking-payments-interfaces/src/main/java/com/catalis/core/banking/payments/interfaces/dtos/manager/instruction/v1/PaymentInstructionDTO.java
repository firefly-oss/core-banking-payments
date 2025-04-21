package com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.banking.payments.interfaces.enums.manager.instruction.v1.InstructionTypeEnum;
import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentStatusEnum;
import com.catalis.core.utils.annotations.FilterableId;
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
}
