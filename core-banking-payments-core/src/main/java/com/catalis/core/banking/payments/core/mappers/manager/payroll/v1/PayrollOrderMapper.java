package com.catalis.core.banking.payments.core.mappers.manager.payroll.v1;

import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PayrollOrderDTO;
import com.catalis.core.banking.payments.models.entities.manager.payroll.v1.PayrollOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayrollOrderMapper {
    PayrollOrderDTO toDTO (PayrollOrder payrollOrder);
    PayrollOrder toEntity (PayrollOrderDTO payrollOrderDTO);
}
