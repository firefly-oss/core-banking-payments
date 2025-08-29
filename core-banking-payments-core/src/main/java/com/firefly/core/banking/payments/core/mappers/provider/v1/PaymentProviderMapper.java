package com.firefly.core.banking.payments.core.mappers.provider.v1;

import com.firefly.core.banking.payments.interfaces.dtos.provider.v1.PaymentProviderDTO;
import com.firefly.core.banking.payments.models.entities.provider.v1.PaymentProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentProviderMapper {
    PaymentProviderDTO toDTO (PaymentProvider paymentProvider);
    PaymentProvider toEntity (PaymentProviderDTO paymentProviderDTO);
}
