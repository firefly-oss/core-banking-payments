package com.catalis.core.banking.payments.core.mappers.manager.audit.v1;

import com.catalis.core.banking.payments.interfaces.dtos.audit.v1.PaymentAuditDTO;
import com.catalis.core.banking.payments.models.entities.audit.v1.PaymentAudit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentAuditMapper {
    PaymentAuditDTO toDTO(PaymentAudit paymentAudit);
    PaymentAudit toEntity(PaymentAuditDTO paymentAuditDTO);
}
