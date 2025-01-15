package com.catalis.core.banking.payments.web.controllers.manager.payroll.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.services.manager.payroll.v1.PayrollOrderManagerServiceImpl;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PayrollOrderDTO;
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

@Tag(name = "Payroll Orders (Manager)",
        description = "APIs for managing payroll orders within a payment order in a manager context")
@RestController
@RequestMapping("/api/v1/manager/payment-orders/{paymentOrderId}/payroll-orders")
public class PayrollOrderManagerController {

    @Autowired
    private PayrollOrderManagerServiceImpl service;

    @Operation(
            summary = "Retrieve Payroll Orders",
            description = "Retrieves a paginated list of payroll orders for the specified payment order, with optional filtering."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payroll orders",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "404", description = "No payroll orders found", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<PayrollOrderDTO>>> getAllPayrollOrders(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @ParameterObject
            @ModelAttribute FilterRequest<PayrollOrderDTO> filterRequest
    ) {
        return service.getAllPayrollOrders(paymentOrderId, filterRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create Payroll Order",
            description = "Creates a new payroll order for the specified payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payroll order created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PayrollOrderDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid payroll order data provided", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PayrollOrderDTO>> createPayrollOrder(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @Parameter(description = "Payroll order data to be created", required = true,
                    schema = @Schema(implementation = PayrollOrderDTO.class))
            @RequestBody(required = false) PayrollOrderDTO dto
    ) {
        return service.createPayrollOrder(paymentOrderId, dto)
                .map(createdOrder -> ResponseEntity.status(HttpStatus.CREATED).body(createdOrder))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(
            summary = "Get Payroll Order by ID",
            description = "Retrieve a specific payroll order by its unique identifier, within the context of a payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the payroll order",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PayrollOrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Payroll order not found", content = @Content)
    })
    @GetMapping(value = "/{payrollOrderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PayrollOrderDTO>> getPayrollOrderById(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @Parameter(description = "Unique identifier of the payroll order", required = true)
            @PathVariable Long payrollOrderId
    ) {
        return service.getPayrollOrderById(paymentOrderId, payrollOrderId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update Payroll Order",
            description = "Update an existing payroll order by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payroll order updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PayrollOrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Payroll order not found", content = @Content)
    })
    @PutMapping(value = "/{payrollOrderId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PayrollOrderDTO>> updatePayrollOrder(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @Parameter(description = "Unique identifier of the payroll order to update", required = true)
            @PathVariable Long payrollOrderId,

            @Parameter(description = "Updated payroll order data", required = true,
                    schema = @Schema(implementation = PayrollOrderDTO.class))
            @RequestBody(required = false) PayrollOrderDTO dto
    ) {
        return service.updatePayrollOrder(paymentOrderId, payrollOrderId, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete Payroll Order",
            description = "Remove an existing payroll order by its unique identifier, within the context of a payment order."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payroll order deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Payroll order not found", content = @Content)
    })
    @DeleteMapping("/{payrollOrderId}")
    public Mono<ResponseEntity<Void>> deletePayrollOrder(
            @Parameter(description = "Unique identifier of the payment order", required = true)
            @PathVariable Long paymentOrderId,

            @Parameter(description = "Unique identifier of the payroll order to delete", required = true)
            @PathVariable Long payrollOrderId
    ) {
        return service.deletePayrollOrder(paymentOrderId, payrollOrderId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}