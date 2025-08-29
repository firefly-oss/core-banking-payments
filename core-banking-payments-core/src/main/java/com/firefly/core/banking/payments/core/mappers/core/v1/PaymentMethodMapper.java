package com.firefly.core.banking.payments.core.mappers.core.v1;

import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentMethodDTO;
import com.firefly.core.banking.payments.models.entities.core.v1.PaymentMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethodDTO toDTO (PaymentMethod paymentMethod);
    PaymentMethod toEntity (PaymentMethodDTO paymentMethodDTO);
}
