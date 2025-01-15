package com.catalis.core.banking.payments.web.controllers.payments.sepa.v1.dd;

import com.catalis.core.banking.payments.core.services.payments.sepa.v1.dd.SepaDirectDebitServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitCancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd.SepaDirectDebitSimulationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "SEPA Direct Debit",
        description = "APIs for handling SEPA Direct Debit payments (simulation, execution, schedule, cancellation).")
@RestController
@RequestMapping("/api/v1/payments/sepa-direct-debit/{accountId}")
public class SepaDirectDebitController {

    @Autowired
    private SepaDirectDebitServiceImpl service;

    @Operation(summary = "Simulate a SEPA Direct Debit Payment")
    @PostMapping(value = "/simulate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayment(
            @PathVariable Long accountId,
            @RequestBody SepaDirectDebitSimulationDTO simulationDTO
    ) {
        return service.simulatePayment(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute a SEPA Direct Debit Payment")
    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayment(
            @PathVariable Long accountId,
            @RequestBody SepaDirectDebitExecutionDTO executionDTO
    ) {
        return service.executePayment(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Schedule a SEPA Direct Debit Payment")
    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> schedulePayment(
            @PathVariable Long accountId,
            @RequestBody SepaDirectDebitScheduleDTO scheduleDTO
    ) {
        return service.schedulePayment(accountId, scheduleDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Cancel a SEPA Direct Debit Payment")
    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> cancelPayment(
            @PathVariable Long accountId,
            @RequestBody SepaDirectDebitCancelDTO cancelDTO
    ) {
        return service.cancelPayment(accountId, cancelDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}