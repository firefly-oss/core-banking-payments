package com.firefly.core.banking.payments.web.controllers.correspondence.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.services.correspondence.v1.PaymentCorrespondenceManagerServiceImpl;
import com.firefly.core.banking.payments.interfaces.dtos.correspondence.v1.PaymentCorrespondenceDTO;
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

@Tag(name = "Payment Correspondence", description = "APIs for managing payment correspondence")
@RestController
@RequestMapping("/api/v1/payment-orders/{paymentOrderId}/correspondence")
public class PaymentCorrespondenceManagerController {

    @Autowired
    private PaymentCorrespondenceManagerServiceImpl service;

    @Operation(
            summary = "Retrieve Correspondence",
            description = "Retrieves a paginated list of correspondence for a specific payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved correspondence",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No correspondence found", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<PaymentCorrespondenceDTO>>> getCorrespondenceByPaymentOrderId(
            @Parameter(description = "Payment Order ID", required = true)
            @PathVariable Long paymentOrderId,
            @ParameterObject
            @ModelAttribute FilterRequest<PaymentCorrespondenceDTO> filterRequest
    ) {
        return service.getCorrespondenceByPaymentOrderId(paymentOrderId, filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Retrieve Correspondence by Type",
            description = "Retrieves a paginated list of correspondence for a specific payment order and correspondence type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved correspondence",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No correspondence found", content = @Content)
    })
    @GetMapping(value = "/type/{correspondenceType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<PaymentCorrespondenceDTO>>> getCorrespondenceByPaymentOrderIdAndType(
            @Parameter(description = "Payment Order ID", required = true)
            @PathVariable Long paymentOrderId,
            @Parameter(description = "Correspondence Type", required = true)
            @PathVariable String correspondenceType,
            @ParameterObject
            @ModelAttribute FilterRequest<PaymentCorrespondenceDTO> filterRequest
    ) {
        return service.getCorrespondenceByPaymentOrderIdAndType(paymentOrderId, correspondenceType, filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Correspondence",
            description = "Creates a new correspondence for a payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created correspondence",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentCorrespondenceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentCorrespondenceDTO>> createCorrespondence(
            @Parameter(description = "Payment Order ID", required = true)
            @PathVariable Long paymentOrderId,
            @RequestBody PaymentCorrespondenceDTO paymentCorrespondenceDTO
    ) {
        return service.createCorrespondence(paymentOrderId, paymentCorrespondenceDTO)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
    }

    @Operation(
            summary = "Get Correspondence by ID",
            description = "Retrieves a specific correspondence by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved correspondence",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentCorrespondenceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Correspondence not found", content = @Content)
    })
    @GetMapping(value = "/{paymentCorrespondenceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentCorrespondenceDTO>> getCorrespondenceById(
            @Parameter(description = "Payment Order ID", required = true)
            @PathVariable Long paymentOrderId,
            @Parameter(description = "Payment Correspondence ID", required = true)
            @PathVariable Long paymentCorrespondenceId
    ) {
        return service.getCorrespondenceById(paymentCorrespondenceId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update Correspondence",
            description = "Updates a correspondence for a payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated correspondence",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaymentCorrespondenceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Correspondence not found", content = @Content)
    })
    @PutMapping(value = "/{paymentCorrespondenceId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaymentCorrespondenceDTO>> updateCorrespondence(
            @Parameter(description = "Payment Order ID", required = true)
            @PathVariable Long paymentOrderId,
            @Parameter(description = "Payment Correspondence ID", required = true)
            @PathVariable Long paymentCorrespondenceId,
            @RequestBody PaymentCorrespondenceDTO paymentCorrespondenceDTO
    ) {
        return service.updateCorrespondence(paymentOrderId, paymentCorrespondenceId, paymentCorrespondenceDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete Correspondence",
            description = "Deletes a correspondence for a payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted correspondence"),
            @ApiResponse(responseCode = "404", description = "Correspondence not found", content = @Content)
    })
    @DeleteMapping(value = "/{paymentCorrespondenceId}")
    public Mono<ResponseEntity<Void>> deleteCorrespondence(
            @Parameter(description = "Payment Order ID", required = true)
            @PathVariable Long paymentOrderId,
            @Parameter(description = "Payment Correspondence ID", required = true)
            @PathVariable Long paymentCorrespondenceId
    ) {
        return service.deleteCorrespondence(paymentCorrespondenceId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
