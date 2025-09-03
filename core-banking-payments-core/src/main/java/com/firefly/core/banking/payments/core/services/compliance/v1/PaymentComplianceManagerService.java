package com.firefly.core.banking.payments.core.services.compliance.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.compliance.v1.PaymentComplianceDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentComplianceManagerService {
    
    Mono<PaginationResponse<PaymentComplianceDTO>> getComplianceByPaymentOrderId(UUID paymentOrderId, FilterRequest<PaymentComplianceDTO> filterRequest);
    
    Mono<PaymentComplianceDTO> createCompliance(UUID paymentOrderId, PaymentComplianceDTO paymentComplianceDTO);
    
    Mono<PaymentComplianceDTO> getComplianceById(UUID paymentComplianceId);
    
    Mono<PaymentComplianceDTO> updateCompliance(UUID paymentOrderId, UUID paymentComplianceId, PaymentComplianceDTO paymentComplianceDTO);
    
    Mono<PaymentComplianceDTO> approveCompliance(UUID paymentOrderId, String approvedBy, String complianceNotes);
    
    Mono<PaymentComplianceDTO> rejectCompliance(UUID paymentOrderId, String rejectionReason);
    
    Mono<Void> deleteCompliance(UUID paymentComplianceId);
}
