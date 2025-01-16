package com.catalis.core.banking.payments.web.controllers.payments.sepa.v1.dd;

import com.catalis.core.banking.payments.core.services.payments.sepa.v1.dd.SepaDirectDebitService;
import com.catalis.core.banking.payments.core.services.payments.sepa.v1.dd.SepaDirectDebitServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "SEPA Direct Debit (DD)",
        description = "APIs for handling SEPA Direct Debit payments (simulate, execute, schedule, delete).")
@RestController
@RequestMapping("/api/v1/payments/sepa-direct-debit/{accountId}")
public class SepaDirectDebitController {

    @Autowired
    private SepaDirectDebitServiceImpl service;

    @Operation(summary = "Simulate a SEPA Direct Debit Payment (triggers OTP if needed)")
    @PostMapping(value = "/execute/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayment(
            @PathVariable Long accountId,
            @RequestBody DirectDebitPaymentSimulationRequestDTO simulationDTO
    ) {
        return service.simulatePayment(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute a SEPA Direct Debit Payment (consumes OTP if required)")
    @PostMapping(value = "/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayment(
            @PathVariable Long accountId,
            @RequestBody DirectDebitPaymentExecutionRequestDTO executionDTO
    ) {
        return service.executePayment(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @Operation(summary = "Simulate Cancel of a SEPA Direct Debit Payment (triggers OTP)")
    @DeleteMapping(value = "/cancel/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateDelete(
            @PathVariable Long accountId,
            @RequestBody DirectDebitPaymentSimulationRequestDTO deleteSimulationDTO
    ) {
        return service.simulateDeletion(accountId, deleteSimulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @Operation(summary = "Execute Cancel of a SEPA Direct Debit Payment (consumes OTP)")
    @DeleteMapping(value = "/cancel",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeDelete(
            @PathVariable Long accountId,
            @RequestBody DirectDebitPaymentExecutionRequestDTO deleteExecutionDTO
    ) {
        return service.executeDeletion(accountId, deleteExecutionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // CREATE (simulate + execute)
    @PostMapping("/periodic/simulate")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePeriodic(
            @PathVariable Long accountId,
            @RequestBody DirectDebitPeriodicPaymentSimulationRequestDTO requestDTO
    ) {
        return service.simulatePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/periodic/execute")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePeriodic(
            @PathVariable Long accountId,
            @RequestBody DirectDebitPeriodicPaymentExecutionRequestDTO requestDTO
    ) {
        return service.executePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // CANCEL (simulate + execute)
    @DeleteMapping("/periodic/cancel/simulate")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody DirectDebitPeriodicPaymentCancelSimulationRequestDTO requestDTO
    ) {
        return service.simulateCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/periodic/cancel/execute")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody DirectDebitPeriodicPaymentCancelExecutionRequestDTO requestDTO
    ) {
        return service.executeCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}