package com.catalis.core.banking.payments.core.services.correspondence.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.interfaces.dtos.correspondence.v1.PaymentCorrespondenceDTO;
import reactor.core.publisher.Mono;

public interface PaymentCorrespondenceManagerService {
    
    Mono<PaginationResponse<PaymentCorrespondenceDTO>> getCorrespondenceByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentCorrespondenceDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentCorrespondenceDTO>> getCorrespondenceByPaymentOrderIdAndType(Long paymentOrderId, String correspondenceType, FilterRequest<PaymentCorrespondenceDTO> filterRequest);
    
    Mono<PaymentCorrespondenceDTO> createCorrespondence(Long paymentOrderId, PaymentCorrespondenceDTO paymentCorrespondenceDTO);
    
    Mono<PaymentCorrespondenceDTO> getCorrespondenceById(Long paymentCorrespondenceId);
    
    Mono<PaymentCorrespondenceDTO> updateCorrespondence(Long paymentOrderId, Long paymentCorrespondenceId, PaymentCorrespondenceDTO paymentCorrespondenceDTO);
    
    Mono<Void> deleteCorrespondence(Long paymentCorrespondenceId);
}
