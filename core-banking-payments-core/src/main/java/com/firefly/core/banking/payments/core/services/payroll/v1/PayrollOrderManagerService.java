package com.firefly.core.banking.payments.core.services.payroll.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.payroll.v1.PayrollOrderDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PayrollOrderManagerService {

    /**
     * Retrieves a paginated list of payroll orders for a given payment order.
     */
    Mono<PaginationResponse<PayrollOrderDTO>> getAllPayrollOrders(UUID paymentOrderId,
                                                                  FilterRequest<PayrollOrderDTO> filterRequest);

    /**
     * Creates a new payroll order for the specified payment order.
     */
    Mono<PayrollOrderDTO> createPayrollOrder(UUID paymentOrderId, PayrollOrderDTO payrollOrderDTO);

    /**
     * Retrieves a specific payroll order by its unique identifier, within the context of a payment order.
     */
    Mono<PayrollOrderDTO> getPayrollOrderById(UUID paymentOrderId, UUID payrollOrderId);

    /**
     * Updates an existing payroll order by its unique identifier.
     */
    Mono<PayrollOrderDTO> updatePayrollOrder(UUID paymentOrderId, UUID payrollOrderId, PayrollOrderDTO payrollOrderDTO);

    /**
     * Deletes a specific payroll order by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePayrollOrder(UUID paymentOrderId, UUID payrollOrderId);
}