package com.catalis.core.banking.payments.core.mappers.manager.exchange.v1;

import com.catalis.core.banking.payments.interfaces.dtos.exchange.v1.PaymentExchangeRateDTO;
import com.catalis.core.banking.payments.models.entities.exchange.v1.PaymentExchangeRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentExchangeRateMapper {
    PaymentExchangeRateDTO toDTO(PaymentExchangeRate paymentExchangeRate);
    PaymentExchangeRate toEntity(PaymentExchangeRateDTO paymentExchangeRateDTO);
}
