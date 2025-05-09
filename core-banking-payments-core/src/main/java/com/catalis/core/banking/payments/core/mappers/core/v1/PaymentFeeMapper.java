package com.catalis.core.banking.payments.core.mappers.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.core.v1.PaymentFeeDTO;
import com.catalis.core.banking.payments.models.entities.core.v1.PaymentFee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentFeeMapper {
    PaymentFeeDTO toDTO (PaymentFee paymentFee);
    PaymentFee toEntity (PaymentFeeDTO paymentFeeDTO);
}
