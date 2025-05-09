package com.catalis.core.banking.payments.core.services.audit.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.interfaces.dtos.audit.v1.PaymentAuditDTO;
import reactor.core.publisher.Mono;

public interface PaymentAuditManagerService {
    
    Mono<PaginationResponse<PaymentAuditDTO>> getAuditsByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentAuditDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentAuditDTO>> getAuditsByPaymentInstructionId(Long paymentInstructionId, FilterRequest<PaymentAuditDTO> filterRequest);
    
    Mono<PaymentAuditDTO> createAudit(PaymentAuditDTO paymentAuditDTO);
    
    Mono<PaymentAuditDTO> getAuditById(Long paymentAuditId);
}
