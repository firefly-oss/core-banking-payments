package com.catalis.core.banking.payments.core.services.payments.sepa.v1.sct;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctSimulationDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SepaSctServiceImpl implements SepaSctService {

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SepaSctSimulationDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SepaSctExecutionDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> schedulePayment(Long accountId, SepaSctScheduleDTO scheduleDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> cancelPayment(Long accountId, SepaSctCancelDTO cancelDTO) {
        return null;
    }
}