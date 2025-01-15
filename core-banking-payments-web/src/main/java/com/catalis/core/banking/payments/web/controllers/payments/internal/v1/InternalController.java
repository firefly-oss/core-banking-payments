package com.catalis.core.banking.payments.web.controllers.payments.internal.v1;

import com.catalis.core.banking.payments.core.services.payments.internal.v1.InternalPaymentServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.InternalSimulationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Internal Payments", description = "APIs for intra-bank payment operations (simulation, execution, scheduling, and cancellation).")
@RestController
@RequestMapping("/api/v1/payments/internal/{accountId}")
public class InternalController {

    @Autowired
    private InternalPaymentServiceImpl service;

    @Operation(summary = "Simulate an Internal Payment")
    @PostMapping(value = "/simulate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayment(
            @PathVariable Long accountId,
            @RequestBody InternalSimulationDTO simulationDTO
    ) {
        return service.simulatePayment(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute an Internal Payment")
    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayment(
            @PathVariable Long accountId,
            @RequestBody InternalExecutionDTO executionDTO
    ) {
        return service.executePayment(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Schedule an Internal Payment")
    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> schedulePayment(
            @PathVariable Long accountId,
            @RequestBody InternalScheduleDTO scheduleDTO
    ) {
        return service.schedulePayment(accountId, scheduleDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Cancel an Internal Payment")
    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> cancelPayment(
            @PathVariable Long accountId,
            @RequestBody InternalCancelDTO cancelDTO
    ) {
        return service.cancelPayment(accountId, cancelDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}