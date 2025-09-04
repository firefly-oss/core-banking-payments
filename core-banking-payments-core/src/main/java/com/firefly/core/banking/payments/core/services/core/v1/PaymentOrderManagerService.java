/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.banking.payments.core.services.core.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentOrderDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentOrderManagerService {

    /**
     * Retrieves a paginated list of payment orders based on various criteria.
     */
    Mono<PaginationResponse<PaymentOrderDTO>> getAllPaymentOrders(FilterRequest<PaymentOrderDTO> filterRequest);

    /**
     * Creates a new payment order.
     */
    Mono<PaymentOrderDTO> createPaymentOrder(PaymentOrderDTO paymentOrderDTO);

    /**
     * Retrieves a specific payment order by its unique identifier.
     */
    Mono<PaymentOrderDTO> getPaymentOrderById(UUID paymentOrderId);

    /**
     * Updates an existing payment order by its unique identifier.
     */
    Mono<PaymentOrderDTO> updatePaymentOrder(UUID paymentOrderId, PaymentOrderDTO paymentOrderDTO);

    /**
     * Deletes a specific payment order by its unique identifier.
     */
    Mono<Void> deletePaymentOrder(UUID paymentOrderId);
}
