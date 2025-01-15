package com.catalis.core.banking.payments.core.services.payments.sepa.v1.dd;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitSimulationDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SepaDirectDebitServiceImpl implements SepaDirectDebitService {

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SepaDirectDebitSimulationDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SepaDirectDebitExecutionDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> schedulePayment(Long accountId, SepaDirectDebitScheduleDTO scheduleDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> cancelPayment(Long accountId, SepaDirectDebitCancelDTO cancelDTO) {
        return null;
    }
}
