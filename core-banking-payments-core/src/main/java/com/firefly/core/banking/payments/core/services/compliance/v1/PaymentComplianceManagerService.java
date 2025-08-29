package com.firefly.core.banking.payments.core.services.compliance.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.compliance.v1.PaymentComplianceDTO;
import reactor.core.publisher.Mono;

public interface PaymentComplianceManagerService {
    
    Mono<PaginationResponse<PaymentComplianceDTO>> getComplianceByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentComplianceDTO> filterRequest);
    
    Mono<PaymentComplianceDTO> createCompliance(Long paymentOrderId, PaymentComplianceDTO paymentComplianceDTO);
    
    Mono<PaymentComplianceDTO> getComplianceById(Long paymentComplianceId);
    
    Mono<PaymentComplianceDTO> updateCompliance(Long paymentOrderId, Long paymentComplianceId, PaymentComplianceDTO paymentComplianceDTO);
    
    Mono<PaymentComplianceDTO> approveCompliance(Long paymentOrderId, String approvedBy, String complianceNotes);
    
    Mono<PaymentComplianceDTO> rejectCompliance(Long paymentOrderId, String rejectionReason);
    
    Mono<Void> deleteCompliance(Long paymentComplianceId);
}
