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


package com.firefly.core.banking.payments.core.services.audit.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.audit.v1.PaymentAuditDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentAuditManagerService {
    
    Mono<PaginationResponse<PaymentAuditDTO>> getAuditsByPaymentOrderId(UUID paymentOrderId, FilterRequest<PaymentAuditDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentAuditDTO>> getAuditsByPaymentInstructionId(UUID paymentInstructionId, FilterRequest<PaymentAuditDTO> filterRequest);
    
    Mono<PaymentAuditDTO> createAudit(PaymentAuditDTO paymentAuditDTO);
    
    Mono<PaymentAuditDTO> getAuditById(UUID paymentAuditId);
}
