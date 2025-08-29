package com.firefly.core.banking.payments.core.mappers.payroll.v1;

import com.firefly.core.banking.payments.interfaces.dtos.payroll.v1.PayrollOrderDTO;
import com.firefly.core.banking.payments.models.entities.payroll.v1.PayrollOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayrollOrderMapper {
    PayrollOrderDTO toDTO (PayrollOrder payrollOrder);
    PayrollOrder toEntity (PayrollOrderDTO payrollOrderDTO);
}
