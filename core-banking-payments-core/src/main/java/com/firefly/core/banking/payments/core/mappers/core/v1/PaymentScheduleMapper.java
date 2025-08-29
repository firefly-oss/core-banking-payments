package com.firefly.core.banking.payments.core.mappers.core.v1;

import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentScheduleDTO;
import com.firefly.core.banking.payments.models.entities.core.v1.PaymentSchedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentScheduleMapper {
    PaymentScheduleDTO toDTO (PaymentSchedule paymentSchedule);
    PaymentSchedule toEntity (PaymentScheduleDTO paymentScheduleDTO);
}
