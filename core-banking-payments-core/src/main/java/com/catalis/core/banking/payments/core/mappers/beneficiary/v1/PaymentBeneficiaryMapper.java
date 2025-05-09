package com.catalis.core.banking.payments.core.mappers.manager.beneficiary.v1;

import com.catalis.core.banking.payments.interfaces.dtos.beneficiary.v1.PaymentBeneficiaryDTO;
import com.catalis.core.banking.payments.models.entities.beneficiary.v1.PaymentBeneficiary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentBeneficiaryMapper {
    PaymentBeneficiaryDTO toDTO(PaymentBeneficiary paymentBeneficiary);
    PaymentBeneficiary toEntity(PaymentBeneficiaryDTO paymentBeneficiaryDTO);
}
