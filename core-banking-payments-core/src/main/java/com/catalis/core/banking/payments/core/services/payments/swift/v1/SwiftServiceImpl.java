package com.catalis.core.banking.payments.core.services.payments.swift.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftSimulationDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SwiftServiceImpl implements SwiftService {

    /*
     * TODO: Here is where the magic happends using the abstraction from lib-baas-provider
     */

    @Override
    public Mono<PaymentOperationResponseDTO> simulatePayment(Long accountId, SwiftSimulationDTO simulationDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> executePayment(Long accountId, SwiftExecutionDTO executionDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> schedulePayment(Long accountId, SwiftScheduleDTO scheduleDTO) {
        return null;
    }

    @Override
    public Mono<PaymentOperationResponseDTO> cancelPayment(Long accountId, SwiftCancelDTO cancelDTO) {
        return null;
    }
}
