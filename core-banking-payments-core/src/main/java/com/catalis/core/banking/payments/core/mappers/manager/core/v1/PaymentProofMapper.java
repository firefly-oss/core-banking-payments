package com.catalis.core.banking.payments.core.mappers.manager.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentProofDTO;
import com.catalis.core.banking.payments.models.entities.manager.core.v1.PaymentProof;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentProofMapper {
    PaymentProofDTO toDTO (PaymentProof paymentProof);
    PaymentProof toEntity (PaymentProofDTO paymentProofDTO);
}
