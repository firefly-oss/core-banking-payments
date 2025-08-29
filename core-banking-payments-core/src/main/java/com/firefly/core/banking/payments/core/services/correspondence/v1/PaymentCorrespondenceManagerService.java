package com.firefly.core.banking.payments.core.services.correspondence.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.correspondence.v1.PaymentCorrespondenceDTO;
import reactor.core.publisher.Mono;

public interface PaymentCorrespondenceManagerService {
    
    Mono<PaginationResponse<PaymentCorrespondenceDTO>> getCorrespondenceByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentCorrespondenceDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentCorrespondenceDTO>> getCorrespondenceByPaymentOrderIdAndType(Long paymentOrderId, String correspondenceType, FilterRequest<PaymentCorrespondenceDTO> filterRequest);
    
    Mono<PaymentCorrespondenceDTO> createCorrespondence(Long paymentOrderId, PaymentCorrespondenceDTO paymentCorrespondenceDTO);
    
    Mono<PaymentCorrespondenceDTO> getCorrespondenceById(Long paymentCorrespondenceId);
    
    Mono<PaymentCorrespondenceDTO> updateCorrespondence(Long paymentOrderId, Long paymentCorrespondenceId, PaymentCorrespondenceDTO paymentCorrespondenceDTO);
    
    Mono<Void> deleteCorrespondence(Long paymentCorrespondenceId);
}
