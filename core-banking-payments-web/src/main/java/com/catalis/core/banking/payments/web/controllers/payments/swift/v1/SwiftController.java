package com.catalis.core.banking.payments.web.controllers.payments.swift.v1;

import com.catalis.core.banking.payments.core.services.payments.swift.v1.SwiftServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "SWIFT Payments",
        description = "APIs for managing SWIFT payments, including simulation, execution, scheduling, and deletion.")
@RestController
@RequestMapping("/api/v1/payments/swift/{accountId}")
public class SwiftController {

    @Autowired
    private SwiftServiceImpl service;

    @Operation(summary = "Simulate a SWIFT Payment (triggers OTP if required)")
    @PostMapping(value = "/execute/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayment(
            @PathVariable Long accountId,
            @RequestBody SwiftPaymentSimulationRequestDTO simulationDTO
    ) {
        return service.simulatePayment(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @Operation(summary = "Execute a SWIFT Payment (consumes OTP if required)")
    @PostMapping(value = "/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayment(
            @PathVariable Long accountId,
            @RequestBody SwiftPaymentExecutionRequestDTO executionDTO
    ) {
        return service.executePayment(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @Operation(summary = "Simulate Deletion of a SWIFT Payment (triggers OTP)")
    @DeleteMapping(value = "/delete/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateDelete(
            @PathVariable Long accountId,
            @RequestBody SwiftPaymentSimulationRequestDTO deleteSimulationDTO
    ) {
        return service.simulateDeletion(accountId, deleteSimulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute Deletion of a SWIFT Payment (consumes OTP)")
    @DeleteMapping(value = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeDelete(
            @PathVariable Long accountId,
            @RequestBody SwiftPaymentExecutionRequestDTO deleteExecutionDTO
    ) {
        return service.executeDeletion(accountId, deleteExecutionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // CREATE (simulate + execute)
    @PostMapping("/periodic/simulate")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePeriodic(
            @PathVariable Long accountId,
            @RequestBody SwiftPeriodicPaymentSimulationRequestDTO requestDTO
    ) {
        return service.simulatePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/periodic/execute")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePeriodic(
            @PathVariable Long accountId,
            @RequestBody SwiftPeriodicPaymentExecutionRequestDTO requestDTO
    ) {
        return service.executePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // CANCEL (simulate + execute)
    @DeleteMapping("/periodic/cancel/simulate")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody SwiftPeriodicPaymentCancelSimulationRequestDTO requestDTO
    ) {
        return service.simulateCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/periodic/cancel/execute")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody SwiftPeriodicPaymentCancelExecutionRequestDTO requestDTO
    ) {
        return service.executeCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}