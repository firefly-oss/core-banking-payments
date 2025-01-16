package com.catalis.core.banking.payments.web.controllers.payments.payroll.v1;

import com.catalis.core.banking.payments.core.services.payments.payroll.v1.PayrollNorma34ServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Payroll (Norma 34)",
        description = "APIs for handling payroll operations (Norma 34) such as simulation, execution, scheduling, cancellation, and deletion.")
@RestController
@RequestMapping("/api/v1/payments/payroll/{accountId}")
public class PayrollController {

    @Autowired
    private PayrollNorma34ServiceImpl service;

    @Operation(summary = "Simulate Payroll (Norma 34) - triggers OTP if required")
    @PostMapping(value = "/execute/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayroll(
            @PathVariable Long accountId,
            @RequestBody Norma34PaymentSimulationRequestDTO simulationDTO
    ) {
        return service.simulatePayroll(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @Operation(summary = "Execute Payroll (Norma 34) - consumes OTP if required")
    @PostMapping(value = "/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayroll(
            @PathVariable Long accountId,
            @RequestBody Norma34PaymentExecutionRequestDTO executionDTO
    ) {
        return service.executePayroll(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @Operation(summary = "Simulate Cancel of Payroll (Norma 34) - triggers OTP")
    @DeleteMapping(value = "/cancel/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateDelete(
            @PathVariable Long accountId,
            @RequestBody Norma34PaymentSimulationRequestDTO deleteSimulationDTO
    ) {
        return service.simulateDeletion(accountId, deleteSimulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute Cancel of Payroll (Norma 34) - consumes OTP")
    @DeleteMapping(value = "/cancel",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeDelete(
            @PathVariable Long accountId,
            @RequestBody Norma34PaymentExecutionRequestDTO deleteExecutionDTO
    ) {
        return service.executeDeletion(accountId, deleteExecutionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // CREATE (simulate + execute)
    @PostMapping("/periodic/simulate")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePeriodic(
            @PathVariable Long accountId,
            @RequestBody Norma34PeriodicPaymentSimulationRequestDTO requestDTO
    ) {
        return service.simulatePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/periodic/execute")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePeriodic(
            @PathVariable Long accountId,
            @RequestBody Norma34PeriodicPaymentExecutionRequestDTO requestDTO
    ) {
        return service.executePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // CANCEL (simulate + execute)
    @DeleteMapping("/periodic/cancel/simulate")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody Norma34PeriodicPaymentCancelSimulationRequestDTO requestDTO
    ) {
        return service.simulateCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/periodic/cancel/execute")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody Norma34PeriodicPaymentCancelExecutionRequestDTO requestDTO
    ) {
        return service.executeCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}