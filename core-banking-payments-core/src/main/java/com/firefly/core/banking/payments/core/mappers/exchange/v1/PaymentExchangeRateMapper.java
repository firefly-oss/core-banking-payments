package com.firefly.core.banking.payments.core.mappers.exchange.v1;

import com.firefly.core.banking.payments.interfaces.dtos.exchange.v1.PaymentExchangeRateDTO;
import com.firefly.core.banking.payments.models.entities.exchange.v1.PaymentExchangeRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentExchangeRateMapper {
    PaymentExchangeRateDTO toDTO(PaymentExchangeRate paymentExchangeRate);
    PaymentExchangeRate toEntity(PaymentExchangeRateDTO paymentExchangeRateDTO);
}
