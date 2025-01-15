package com.catalis.core.banking.payments.core.services.payments.sepa.v1.ict;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict.SepaIctExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict.SepaIctSimulationDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SepaIctServiceImpl implements SepaIctService {

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SepaIctSimulationDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SepaIctExecutionDTO executionDTO) {
        return null;
    }
}