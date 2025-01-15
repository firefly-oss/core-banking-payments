package com.catalis.core.banking.payments.core.services.payments.payroll.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34CancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34ExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34ScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34SimulationDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PayrollNorma34ServiceImpl implements PayrollNorma34Service {

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayroll(Long accountId, PayrollNorma34SimulationDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayroll(Long accountId, PayrollNorma34ExecutionDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> schedulePayroll(Long accountId, PayrollNorma34ScheduleDTO scheduleDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> cancelPayroll(Long accountId, PayrollNorma34CancelDTO cancelDTO) {
        return null;
    }
}
