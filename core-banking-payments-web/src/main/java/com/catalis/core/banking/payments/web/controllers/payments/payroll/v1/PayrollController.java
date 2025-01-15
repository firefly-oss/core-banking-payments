package com.catalis.core.banking.payments.web.controllers.payments.payroll.v1;

import com.catalis.core.banking.payments.core.services.payments.payroll.v1.PayrollNorma34ServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34CancelDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34ExecutionDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34ScheduleDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.payroll.v1.PayrollNorma34SimulationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Payroll (Norma 34)",
        description = "APIs for handling payroll operations (Norma 34) such as simulation, execution, scheduling, and cancellation.")
@RestController
@RequestMapping("/api/v1/payments/payroll/{accountId}")
public class PayrollController {

    @Autowired
    private PayrollNorma34ServiceImpl service;

    @Operation(summary = "Simulate Payroll (Norma 34)")
    @PostMapping(value = "/simulate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> simulatePayroll(
            @PathVariable Long accountId,
            @RequestBody PayrollNorma34SimulationDTO simulationDTO
    ) {
        return service.simulatePayroll(accountId, simulationDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Execute Payroll (Norma 34)")
    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> executePayroll(
            @PathVariable Long accountId,
            @RequestBody PayrollNorma34ExecutionDTO executionDTO
    ) {
        return service.executePayroll(accountId, executionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Schedule Payroll (Norma 34)")
    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> schedulePayroll(
            @PathVariable Long accountId,
            @RequestBody PayrollNorma34ScheduleDTO scheduleDTO
    ) {
        return service.schedulePayroll(accountId, scheduleDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Cancel Payroll (Norma 34)")
    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentOperationResponseDTO>> cancelPayroll(
            @PathVariable Long accountId,
            @RequestBody PayrollNorma34CancelDTO cancelDTO
    ) {
        return service.cancelPayroll(accountId, cancelDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}