package com.firefly.core.banking.payments.core.services.audit.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.audit.v1.PaymentAuditDTO;
import reactor.core.publisher.Mono;

public interface PaymentAuditManagerService {
    
    Mono<PaginationResponse<PaymentAuditDTO>> getAuditsByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentAuditDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentAuditDTO>> getAuditsByPaymentInstructionId(Long paymentInstructionId, FilterRequest<PaymentAuditDTO> filterRequest);
    
    Mono<PaymentAuditDTO> createAudit(PaymentAuditDTO paymentAuditDTO);
    
    Mono<PaymentAuditDTO> getAuditById(Long paymentAuditId);
}
