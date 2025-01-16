package com.catalis.core.banking.payments.web.controllers.payments.sepa.v1.ict;

import com.catalis.core.banking.payments.core.services.payments.sepa.v1.ict.SepaIctService;
import com.catalis.core.banking.payments.core.services.payments.sepa.v1.ict.SepaIctServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "SEPA ICT",
        description = "APIs for SEPA Instant Credit Transfers (ICT), including simulation, execution, and deletion.")
@RestController
@RequestMapping("/api/v1/payments/sepa-ict/{accountId}")
public class SepaIctController {

    @Autowired
    private SepaIctServiceImpl service;

    @Operation(summary = "Simulate a SEPA ICT Payment (triggers OTP if required)")
    @PostMapping(value = "/execute/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayment(
            @PathVariable Long accountId,
            @RequestBody SepaIctPaymentSimulationRequestDTO simulationDTO
    ) {
        return service.simulatePayment(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute a SEPA ICT Payment (consumes OTP if required)")
    @PostMapping(value = "/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayment(
            @PathVariable Long accountId,
            @RequestBody SepaIctPaymentExecutionRequestDTO executionDTO
    ) {
        return service.executePayment(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Simulate Cancel of a SEPA ICT Payment (triggers OTP)")
    @DeleteMapping(value = "/cancel/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateDelete(
            @PathVariable Long accountId,
            @RequestBody SepaIctPaymentSimulationRequestDTO deleteSimulationDTO
    ) {
        return service.simulateDeletion(accountId, deleteSimulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute Deletion of a SEPA ICT Payment (consumes OTP)")
    @DeleteMapping(value = "/cancel",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeDelete(
            @PathVariable Long accountId,
            @RequestBody SepaIctPaymentExecutionRequestDTO deleteExecutionDTO
    ) {
        return service.executeDeletion(accountId, deleteExecutionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Simulate creation of a periodic SEPA ICT payment")
    @PostMapping("/periodic/simulate")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePeriodicPayment(
            @PathVariable Long accountId,
            @RequestBody SepaIctPeriodicPaymentSimulationRequestDTO requestDTO
    ) {
        return service.simulatePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute creation of a periodic SEPA ICT payment (consumes OTP)")
    @PostMapping("/periodic/execute")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePeriodicPayment(
            @PathVariable Long accountId,
            @RequestBody SepaIctPeriodicPaymentExecutionRequestDTO requestDTO
    ) {
        return service.executePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Simulate cancellation of a periodic SEPA ICT payment")
    @DeleteMapping("/periodic/cancel/simulate")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody SepaIctPeriodicPaymentCancelSimulationRequestDTO requestDTO
    ) {
        return service.simulateCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute cancellation of a periodic SEPA ICT payment (consumes OTP)")
    @DeleteMapping("/periodic/cancel/execute")
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody SepaIctPeriodicPaymentCancelExecutionRequestDTO requestDTO
    ) {
        return service.executeCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}