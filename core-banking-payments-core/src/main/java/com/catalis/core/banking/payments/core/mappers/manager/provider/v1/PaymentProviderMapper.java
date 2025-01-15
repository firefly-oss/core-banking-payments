package com.catalis.core.banking.payments.core.mappers.manager.provider.v1;

import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentProviderDTO;
import com.catalis.core.banking.payments.models.entities.manager.provider.v1.PaymentProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentProviderMapper {
    PaymentProviderDTO toDTO (PaymentProvider paymentProvider);
    PaymentProvider toEntity (PaymentProviderDTO paymentProviderDTO);
}
