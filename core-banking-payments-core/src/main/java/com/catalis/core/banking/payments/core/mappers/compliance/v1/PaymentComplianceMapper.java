package com.catalis.core.banking.payments.core.mappers.manager.compliance.v1;

import com.catalis.core.banking.payments.interfaces.dtos.compliance.v1.PaymentComplianceDTO;
import com.catalis.core.banking.payments.models.entities.compliance.v1.PaymentCompliance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentComplianceMapper {
    PaymentComplianceDTO toDTO(PaymentCompliance paymentCompliance);
    PaymentCompliance toEntity(PaymentComplianceDTO paymentComplianceDTO);
}
