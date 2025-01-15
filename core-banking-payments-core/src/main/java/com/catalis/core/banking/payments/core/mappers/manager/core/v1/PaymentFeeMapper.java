package com.catalis.core.banking.payments.core.mappers.manager.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentFeeDTO;
import com.catalis.core.banking.payments.models.entities.manager.core.v1.PaymentFee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentFeeMapper {
    PaymentFeeDTO toDTO (PaymentFee paymentFee);
    PaymentFee toEntity (PaymentFeeDTO paymentFeeDTO);
}
