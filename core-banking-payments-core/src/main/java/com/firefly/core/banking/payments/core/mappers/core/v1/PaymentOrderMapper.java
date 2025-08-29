package com.firefly.core.banking.payments.core.mappers.core.v1;

import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentOrderDTO;
import com.firefly.core.banking.payments.models.entities.core.v1.PaymentOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentOrderMapper {
    PaymentOrderDTO toDTO (PaymentOrder paymentOrder);
    PaymentOrder toEntity (PaymentOrderDTO paymentOrderDTO);
}
