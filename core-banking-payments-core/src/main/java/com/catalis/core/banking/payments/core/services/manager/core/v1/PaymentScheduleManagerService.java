package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentScheduleDTO;
import reactor.core.publisher.Mono;

public interface PaymentScheduleManagerService {

    /**
     * Retrieves a paginated list of payment schedules for a given payment order.
     */
    Mono<PaginationResponse<PaymentScheduleDTO>> getAllPaymentSchedules(Long paymentOrderId,
                                                                        FilterRequest<PaymentScheduleDTO> filterRequest);

    /**
     * Creates a new payment schedule for the specified payment order.
     */
    Mono<PaymentScheduleDTO> createPaymentSchedule(Long paymentOrderId, PaymentScheduleDTO paymentScheduleDTO);

    /**
     * Retrieves a specific payment schedule by its unique identifier, within the context of a payment order.
     */
    Mono<PaymentScheduleDTO> getPaymentScheduleById(Long paymentOrderId, Long paymentScheduleId);

    /**
     * Updates an existing payment schedule by its unique identifier.
     */
    Mono<PaymentScheduleDTO> updatePaymentSchedule(Long paymentOrderId,
                                                   Long paymentScheduleId,
                                                   PaymentScheduleDTO paymentScheduleDTO);

    /**
     * Deletes a specific payment schedule by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePaymentSchedule(Long paymentOrderId, Long paymentScheduleId);
}