package com.catalis.core.banking.payments.web.controllers.payments.swift.v1;

import com.catalis.core.banking.payments.core.services.payments.swift.v1.SwiftServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.SwiftSimulationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "SWIFT Payments",
        description = "APIs for managing SWIFT payments, including simulation, execution, scheduling, and cancellation.")
@RestController
@RequestMapping("/api/v1/payments/swift/{accountId}")
public class SwiftController {

    @Autowired
    private SwiftServiceImpl service;

    @Operation(summary = "Simulate a SWIFT Payment")
    @PostMapping(value = "/simulate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayment(
            @PathVariable Long accountId,
            @RequestBody SwiftSimulationDTO simulationDTO
    ) {
        return service.simulatePayment(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute a SWIFT Payment")
    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayment(
            @PathVariable Long accountId,
            @RequestBody SwiftExecutionDTO executionDTO
    ) {
        return service.executePayment(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Schedule a SWIFT Payment")
    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> schedulePayment(
            @PathVariable Long accountId,
            @RequestBody SwiftScheduleDTO scheduleDTO
    ) {
        return service.schedulePayment(accountId, scheduleDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Cancel a SWIFT Payment")
    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> cancelPayment(
            @PathVariable Long accountId,
            @RequestBody SwiftCancelDTO cancelDTO
    ) {
        return service.cancelPayment(accountId, cancelDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}