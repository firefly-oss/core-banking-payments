package com.firefly.core.banking.payments.core.services.correspondence.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.correspondence.v1.PaymentCorrespondenceDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentCorrespondenceManagerService {
    
    Mono<PaginationResponse<PaymentCorrespondenceDTO>> getCorrespondenceByPaymentOrderId(UUID paymentOrderId, FilterRequest<PaymentCorrespondenceDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentCorrespondenceDTO>> getCorrespondenceByPaymentOrderIdAndType(UUID paymentOrderId, String correspondenceType, FilterRequest<PaymentCorrespondenceDTO> filterRequest);
    
    Mono<PaymentCorrespondenceDTO> createCorrespondence(UUID paymentOrderId, PaymentCorrespondenceDTO paymentCorrespondenceDTO);
    
    Mono<PaymentCorrespondenceDTO> getCorrespondenceById(UUID paymentCorrespondenceId);
    
    Mono<PaymentCorrespondenceDTO> updateCorrespondence(UUID paymentOrderId, UUID paymentCorrespondenceId, PaymentCorrespondenceDTO paymentCorrespondenceDTO);
    
    Mono<Void> deleteCorrespondence(UUID paymentCorrespondenceId);
}
