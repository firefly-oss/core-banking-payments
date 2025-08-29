package com.firefly.core.banking.payments.core.mappers.compliance.v1;

import com.firefly.core.banking.payments.interfaces.dtos.compliance.v1.PaymentComplianceDTO;
import com.firefly.core.banking.payments.models.entities.compliance.v1.PaymentCompliance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentComplianceMapper {
    PaymentComplianceDTO toDTO(PaymentCompliance paymentCompliance);
    PaymentCompliance toEntity(PaymentComplianceDTO paymentComplianceDTO);
}
