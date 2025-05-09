package com.catalis.core.banking.payments.web.controllers.provider.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.services.provider.v1.PaymentProviderManagerServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.provider.v1.PaymentProviderDTO;
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

@Tag(name = "Payment Providers",
        description = "APIs for managing payment providers")
@RestController
@RequestMapping("/api/v1/payment-orders/{paymentOrderId}/providers")
public class PaymentProviderManagerController {

    @Autowired
    private PaymentProviderManagerServiceImpl service;

    @Operation(
            summary = "Retrieve Payment Providers",
            description = "Retrieves a paginated list of payment providers for a specific payment order, with optional filtering."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payment providers",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No payment providers found", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<PaymentProviderDTO>>> getAllPaymentProviders(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @ParameterObject
            @ModelAttribute FilterRequest<PaymentProviderDTO> filterRequest
    ) {
        return service.getAllPaymentProviders(paymentOrderId, filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Payment Provider",
            description = "Creates a new payment provider for the specified payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment provider created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentProviderDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid payment provider data provided", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentProviderDTO>> createPaymentProvider(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @Parameter(description = "Payment provider data to be created", required = true,
                    schema = @Schema(implementation = PaymentProviderDTO.class))
            @RequestBody(required = false) PaymentProviderDTO dto
    ) {
        return service.createPaymentProvider(paymentOrderId, dto)
                .map(createdProvider -> ResponseEntity.status(HttpStatus.CREATED).body(createdProvider))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(
            summary = "Get Payment Provider by ID",
            description = "Retrieve a specific payment provider by its unique identifier, within the context of a payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the payment provider",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentProviderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Payment provider not found", content = @Content)
    })
    @GetMapping(value = "/{paymentProviderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentProviderDTO>> getPaymentProviderById(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @Parameter(description = "Unique identifier of the payment provider", required = true)
            @PathVariable Long paymentProviderId
    ) {
        return service.getPaymentProviderById(paymentOrderId, paymentProviderId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update Payment Provider",
            description = "Update an existing payment provider by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment provider updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentProviderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Payment provider not found", content = @Content)
    })
    @PutMapping(value = "/{paymentProviderId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentProviderDTO>> updatePaymentProvider(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @Parameter(description = "Unique identifier of the payment provider to update", required = true)
            @PathVariable Long paymentProviderId,

            @Parameter(description = "Updated payment provider data", required = true,
                    schema = @Schema(implementation = PaymentProviderDTO.class))
            @RequestBody(required = false) PaymentProviderDTO dto
    ) {
        return service.updatePaymentProvider(paymentOrderId, paymentProviderId, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete Payment Provider",
            description = "Remove an existing payment provider by its unique identifier, within the context of a payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment provider deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Payment provider not found", content = @Content)
    })
    @DeleteMapping("/{paymentProviderId}")
    public Mono<ResponseEntity<Void>> deletePaymentProvider(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @Parameter(description = "Unique identifier of the payment provider to delete", required = true)
            @PathVariable Long paymentProviderId
    ) {
        return service.deletePaymentProvider(paymentOrderId, paymentProviderId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}