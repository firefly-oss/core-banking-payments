package com.firefly.core.banking.payments.web.controllers.instruction.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.services.instruction.v1.PaymentInstructionManagerServiceImpl;
import com.firefly.core.banking.payments.interfaces.dtos.instruction.v1.PaymentInstructionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Tag(name = "Payment Instructions",
        description = "APIs for managing payment instructions")
@RestController
@RequestMapping("/api/v1/payment-orders/{paymentOrderId}/instructions")
public class PaymentInstructionManagerController {

    @Autowired
    private PaymentInstructionManagerServiceImpl service;

    @Operation(
            summary = "Retrieve Payment Instructions",
            description = "Retrieves a paginated list of payment instructions based on filtering criteria, for a specific payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payment instructions",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No payment instructions found", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<PaymentInstructionDTO>>> getAllPaymentInstructions(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable UUID paymentOrderId,

            @ParameterObject
            @ModelAttribute FilterRequest<PaymentInstructionDTO> filterRequest
    ) {
        return service.getAllPaymentInstructions(paymentOrderId, filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Payment Instruction",
            description = "Creates a new payment instruction for the specified payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment instruction created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentInstructionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid payment instruction data provided", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentInstructionDTO>> createPaymentInstruction(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable UUID paymentOrderId,

            @Parameter(description = "Payment instruction data to be created", required = true,
                    schema = @Schema(implementation = PaymentInstructionDTO.class))
            @RequestBody(required = false) PaymentInstructionDTO dto
    ) {
        return service.createPaymentInstruction(paymentOrderId, dto)
                .map(createdInstruction -> ResponseEntity.status(HttpStatus.CREATED).body(createdInstruction))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(
            summary = "Get Payment Instruction by ID",
            description = "Retrieve a specific payment instruction by its unique identifier, within the context of a payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the payment instruction",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentInstructionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Payment instruction not found", content = @Content)
    })
    @GetMapping(value = "/{paymentInstructionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentInstructionDTO>> getPaymentInstructionById(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable UUID paymentOrderId,

            @Parameter(description = "Unique identifier of the payment instruction", required = true)
            @PathVariable UUID paymentInstructionId
    ) {
        return service.getPaymentInstructionById(paymentOrderId, paymentInstructionId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update Payment Instruction",
            description = "Update an existing payment instruction by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment instruction updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentInstructionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Payment instruction not found", content = @Content)
    })
    @PutMapping(value = "/{paymentInstructionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentInstructionDTO>> updatePaymentInstruction(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable UUID paymentOrderId,

            @Parameter(description = "Unique identifier of the payment instruction to update", required = true)
            @PathVariable UUID paymentInstructionId,

            @Parameter(description = "Updated payment instruction data", required = true,
                    schema = @Schema(implementation = PaymentInstructionDTO.class))
            @RequestBody(required = false) PaymentInstructionDTO dto
    ) {
        return service.updatePaymentInstruction(paymentOrderId, paymentInstructionId, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete Payment Instruction",
            description = "Remove an existing payment instruction by its unique identifier, within the context of a payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment instruction deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Payment instruction not found", content = @Content)
    })
    @DeleteMapping("/{paymentInstructionId}")
    public Mono<ResponseEntity<Void>> deletePaymentInstruction(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable UUID paymentOrderId,

            @Parameter(description = "Unique identifier of the payment instruction to delete", required = true)
            @PathVariable UUID paymentInstructionId
    ) {
        return service.deletePaymentInstruction(paymentOrderId, paymentInstructionId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}