package com.catalis.core.banking.payments.core.mappers.manager.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentOrderDTO;
import com.catalis.core.banking.payments.models.entities.manager.core.v1.PaymentOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentOrderMapper {
    PaymentOrderDTO toDTO (PaymentOrder paymentOrder);
    PaymentOrder toEntity (PaymentOrderDTO paymentOrderDTO);
}
