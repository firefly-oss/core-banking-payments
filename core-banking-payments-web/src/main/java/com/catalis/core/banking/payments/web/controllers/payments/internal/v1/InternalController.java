package com.catalis.core.banking.payments.web.controllers.payments.internal.v1;

import com.catalis.core.banking.payments.core.services.payments.internal.v1.InternalPaymentServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.internal.v1.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Internal Payments",
        description = "APIs for intra-bank payment operations (simulation, execution, scheduling, cancellation, and deletion).")
@RestController
@RequestMapping("/api/v1/payments/internal/{accountId}")
public class InternalController {

    @Autowired
    private InternalPaymentServiceImpl service;

    @Operation(summary = "Simulate an Internal Payment (triggers OTP)")
    @PostMapping(value = "/execute/simulate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayment(
            @PathVariable Long accountId,
            @RequestBody InternalPaymentSimulationRequestDTO simulationDTO
    ) {
        return service.simulatePayment(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute an Internal Payment (consumes OTP)")
    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayment(
            @PathVariable Long accountId,
            @RequestBody InternalPaymentExecutionRequestDTO executionDTO
    ) {
        return service.executePayment(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @Operation(summary = "Simulate Cancel of an Internal Payment (triggers OTP)")
    @DeleteMapping(value = "/cancel/simulate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateDelete(
            @PathVariable Long accountId,
            @RequestBody InternalPaymentSimulationRequestDTO deleteSimulationDTO
    ) {
        return service.simulateDeletion(accountId, deleteSimulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


    @Operation(summary = "Execute Cancel of an Internal Payment (consumes OTP)")
    @DeleteMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeDelete(
            @PathVariable Long accountId,
            @RequestBody InternalPaymentExecutionRequestDTO deleteExecutionDTO
    ) {
        return service.executeDeletion(accountId, deleteExecutionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Simulate creation of a periodic internal payment (triggers OTP)")
    @PostMapping(value = "/periodic/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePeriodicPayment(
            @PathVariable Long accountId,
            @RequestBody InternalPeriodicPaymentSimulationRequestDTO requestDTO
    ) {
        return service.simulatePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute creation of a periodic internal payment (consumes OTP)")
    @PostMapping(value = "/periodic/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePeriodicPayment(
            @PathVariable Long accountId,
            @RequestBody InternalPeriodicPaymentExecutionRequestDTO requestDTO
    ) {
        return service.executePeriodicPayment(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Simulate cancellation of a periodic internal payment (triggers OTP)")
    @DeleteMapping(value = "/periodic/cancel/simulate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulateCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody InternalPeriodicPaymentCancelSimulationRequestDTO requestDTO
    ) {
        return service.simulateCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute cancellation of a periodic internal payment (consumes OTP)")
    @DeleteMapping(value = "/periodic/cancel/execute",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executeCancelPeriodic(
            @PathVariable Long accountId,
            @RequestBody InternalPeriodicPaymentCancelExecutionRequestDTO requestDTO
    ) {
        return service.executeCancelPeriodic(accountId, requestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

}