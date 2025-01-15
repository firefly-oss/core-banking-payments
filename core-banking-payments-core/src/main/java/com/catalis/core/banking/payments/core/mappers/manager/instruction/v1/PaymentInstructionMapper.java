package com.catalis.core.banking.payments.core.mappers.manager.instruction.v1;

import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentInstructionDTO;
import com.catalis.core.banking.payments.models.entities.manager.instruction.v1.PaymentInstruction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentInstructionMapper {
    PaymentInstructionDTO toDTO (PaymentInstruction paymentInstruction);
    PaymentInstruction toEntity (PaymentInstructionDTO paymentInstructionDTO);
}
