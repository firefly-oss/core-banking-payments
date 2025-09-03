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
