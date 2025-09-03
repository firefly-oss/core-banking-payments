package com.firefly.core.banking.payments.core.services.core.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentScheduleDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentScheduleManagerService {

    /**
     * Retrieves a paginated list of payment schedules for a given payment order.
     */
    Mono<PaginationResponse<PaymentScheduleDTO>> getAllPaymentSchedules(UUID paymentOrderId,
                                                                        FilterRequest<PaymentScheduleDTO> filterRequest);

    /**
     * Creates a new payment schedule for the specified payment order.
     */
    Mono<PaymentScheduleDTO> createPaymentSchedule(UUID paymentOrderId, PaymentScheduleDTO paymentScheduleDTO);

    /**
     * Retrieves a specific payment schedule by its unique identifier, within the context of a payment order.
     */
    Mono<PaymentScheduleDTO> getPaymentScheduleById(UUID paymentOrderId, UUID paymentScheduleId);

    /**
     * Updates an existing payment schedule by its unique identifier.
     */
    Mono<PaymentScheduleDTO> updatePaymentSchedule(UUID paymentOrderId,
                                                   UUID paymentScheduleId,
                                                   PaymentScheduleDTO paymentScheduleDTO);

    /**
     * Deletes a specific payment schedule by its unique identifier, within the context of a payment order.
     */
    Mono<Void> deletePaymentSchedule(UUID paymentOrderId, UUID paymentScheduleId);
}