package com.catalis.core.banking.payments.web.controllers.payments.sepa.v1.sct;

import com.catalis.core.banking.payments.core.services.payments.sepa.v1.sct.SepaSctServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct.SepaSctSimulationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "SEPA SCT",
        description = "APIs for handling SEPA Standard Credit Transfers (SCT) including simulation, execution, scheduling, and cancellation.")
@RestController
@RequestMapping("/api/v1/payments/sepa-sct/{accountId}")
public class SepaSctController {

    @Autowired
    private SepaSctServiceImpl service;

    @Operation(summary = "Simulate a SEPA SCT Payment")
    @PostMapping(value = "/simulate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayment(
            @PathVariable Long accountId,
            @RequestBody SepaSctSimulationDTO simulationDTO
    ) {
        return service.simulatePayment(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute a SEPA SCT Payment")
    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayment(
            @PathVariable Long accountId,
            @RequestBody SepaSctExecutionDTO executionDTO
    ) {
        return service.executePayment(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Schedule a SEPA SCT Payment")
    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> schedulePayment(
            @PathVariable Long accountId,
            @RequestBody SepaSctScheduleDTO scheduleDTO
    ) {
        return service.schedulePayment(accountId, scheduleDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Cancel a SEPA SCT Payment")
    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> cancelPayment(
            @PathVariable Long accountId,
            @RequestBody SepaSctCancelDTO cancelDTO
    ) {
        return service.cancelPayment(accountId, cancelDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}