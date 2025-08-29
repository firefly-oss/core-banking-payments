package com.firefly.core.banking.payments.core.mappers.core.v1;

import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentProofDTO;
import com.firefly.core.banking.payments.models.entities.core.v1.PaymentProof;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentProofMapper {
    PaymentProofDTO toDTO (PaymentProof paymentProof);
    PaymentProof toEntity (PaymentProofDTO paymentProofDTO);
}
