package com.firefly.core.banking.payments.web.controllers.audit.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.services.audit.v1.PaymentAuditManagerServiceImpl;
import com.firefly.core.banking.payments.interfaces.dtos.audit.v1.PaymentAuditDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "Payment Audit", description = "APIs for accessing payment audit records")
@RestController
public class PaymentAuditManagerController {

    @Autowired
    private PaymentAuditManagerServiceImpl service;

    @Operation(
            summary = "Retrieve Audit Records by Payment Order ID",
            description = "Retrieves a paginated list of audit records for a specific payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved audit records",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No audit records found", content = @Content)
    })
    @GetMapping(value = "/api/v1/payment-orders/{paymentOrderId}/audit", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<PaymentAuditDTO>>> getAuditsByPaymentOrderId(
            @Parameter(description = "Payment Order ID", required = true)
            @PathVariable Long paymentOrderId,
            @ParameterObject
            @ModelAttribute FilterRequest<PaymentAuditDTO> filterRequest
    ) {
        return service.getAuditsByPaymentOrderId(paymentOrderId, filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Retrieve Audit Records by Payment Instruction ID",
            description = "Retrieves a paginated list of audit records for a specific payment instruction."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved audit records",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No audit records found", content = @Content)
    })
    @GetMapping(value = "/api/v1/payment-orders/{paymentOrderId}/instructions/{paymentInstructionId}/audit", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<PaymentAuditDTO>>> getAuditsByPaymentInstructionId(
            @Parameter(description = "Payment Order ID", required = true)
            @PathVariable Long paymentOrderId,
            @Parameter(description = "Payment Instruction ID", required = true)
            @PathVariable Long paymentInstructionId,
            @ParameterObject
            @ModelAttribute FilterRequest<PaymentAuditDTO> filterRequest
    ) {
        return service.getAuditsByPaymentInstructionId(paymentInstructionId, filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get Audit Record by ID",
            description = "Retrieves a specific audit record by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved audit record",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentAuditDTO.class))),
            @ApiResponse(responseCode = "404", description = "Audit record not found", content = @Content)
    })
    @GetMapping(value = "/api/v1/audit/{paymentAuditId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentAuditDTO>> getAuditById(
            @Parameter(description = "Payment Audit ID", required = true)
            @PathVariable Long paymentAuditId
    ) {
        return service.getAuditById(paymentAuditId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
