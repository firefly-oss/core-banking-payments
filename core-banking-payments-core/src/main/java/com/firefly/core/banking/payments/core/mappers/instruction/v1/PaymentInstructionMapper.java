package com.firefly.core.banking.payments.core.mappers.instruction.v1;

import com.firefly.core.banking.payments.interfaces.dtos.instruction.v1.PaymentInstructionDTO;
import com.firefly.core.banking.payments.models.entities.instruction.v1.PaymentInstruction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentInstructionMapper {
    PaymentInstructionDTO toDTO (PaymentInstruction paymentInstruction);
    PaymentInstruction toEntity (PaymentInstructionDTO paymentInstructionDTO);
}
