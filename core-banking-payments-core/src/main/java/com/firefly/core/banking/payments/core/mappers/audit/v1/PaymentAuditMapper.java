package com.firefly.core.banking.payments.core.mappers.audit.v1;

import com.firefly.core.banking.payments.interfaces.dtos.audit.v1.PaymentAuditDTO;
import com.firefly.core.banking.payments.models.entities.audit.v1.PaymentAudit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentAuditMapper {
    PaymentAuditDTO toDTO(PaymentAudit paymentAudit);
    PaymentAudit toEntity(PaymentAuditDTO paymentAuditDTO);
}
