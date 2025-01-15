package com.catalis.core.banking.payments.core.mappers.manager.core.v1;

import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentScheduleDTO;
import com.catalis.core.banking.payments.models.entities.manager.core.v1.PaymentSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentScheduleMapper {
    PaymentScheduleDTO toDTO (PaymentSchedule paymentSchedule);
    PaymentSchedule toEntity (PaymentScheduleDTO paymentScheduleDTO);
}
