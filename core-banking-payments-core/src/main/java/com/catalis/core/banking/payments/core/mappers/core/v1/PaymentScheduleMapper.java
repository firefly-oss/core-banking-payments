package com.catalis.core.banking.payments.core.mappers.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.core.v1.PaymentScheduleDTO;
import com.catalis.core.banking.payments.models.entities.core.v1.PaymentSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentScheduleMapper {
    PaymentScheduleDTO toDTO (PaymentSchedule paymentSchedule);
    PaymentSchedule toEntity (PaymentScheduleDTO paymentScheduleDTO);
}
