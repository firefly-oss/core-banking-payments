package com.catalis.core.banking.payments.core.services.manager.payroll.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PayrollOrderDTO;
import reactor.core.publisher.Mono;

public interface PayrollOrderManagerService {

    /**
     * Retrieves a paginated list of payroll orders for a given payment order.
     */
    Mono<PaginationResponse<PayrollOrderDTO>> getAllPayrollOrders(Long paymentOrderId,
                                                                  FilterRequest<PayrollOrderDTO> filterRequest);

    /**
     * Creates a new payroll order for the specified payment order.
     */
    Mono<PayrollOrderDTO> createPayrollOrder(Long paymentOrderId, PayrollOrderDTO payrollOrderDTO);

    /**
     * Retrieves a specific payroll order by its unique identifier, within the context of a payment order.
     */
    Mono<PayrollOrderDTO> getPayrollOrderById(Long paymentOrderId, Long payrollOrderId);

    /**
     * Updates an existing payroll order by its unique identifier.
     */
    Mono<PayrollOrderDTO> updatePayrollOrder(Long paymentOrderId, Long payrollOrderId, PayrollOrderDTO payrollOrderDTO);

    /**
     * Deletes a specific payroll order by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePayrollOrder(Long paymentOrderId, Long payrollOrderId);
}