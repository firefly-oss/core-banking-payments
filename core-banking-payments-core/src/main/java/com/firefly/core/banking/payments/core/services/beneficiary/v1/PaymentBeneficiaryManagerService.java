package com.firefly.core.banking.payments.core.services.beneficiary.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.beneficiary.v1.PaymentBeneficiaryDTO;
import reactor.core.publisher.Mono;

public interface PaymentBeneficiaryManagerService {
    
    Mono<PaginationResponse<PaymentBeneficiaryDTO>> getAllBeneficiaries(FilterRequest<PaymentBeneficiaryDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentBeneficiaryDTO>> getBeneficiariesByPayerAccountId(Long payerAccountId, FilterRequest<PaymentBeneficiaryDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentBeneficiaryDTO>> getFavoriteBeneficiariesByPayerAccountId(Long payerAccountId, FilterRequest<PaymentBeneficiaryDTO> filterRequest);
    
    Mono<PaymentBeneficiaryDTO> createBeneficiary(PaymentBeneficiaryDTO paymentBeneficiaryDTO);
    
    Mono<PaymentBeneficiaryDTO> getBeneficiaryById(Long paymentBeneficiaryId);
    
    Mono<PaymentBeneficiaryDTO> updateBeneficiary(Long paymentBeneficiaryId, PaymentBeneficiaryDTO paymentBeneficiaryDTO);
    
    Mono<PaymentBeneficiaryDTO> markAsFavorite(Long paymentBeneficiaryId, Boolean isFavorite);
    
    Mono<Void> deleteBeneficiary(Long paymentBeneficiaryId);
}
