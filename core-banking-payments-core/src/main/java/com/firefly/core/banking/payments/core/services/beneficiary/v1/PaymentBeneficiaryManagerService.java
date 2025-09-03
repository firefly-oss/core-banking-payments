package com.firefly.core.banking.payments.core.services.beneficiary.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.interfaces.dtos.beneficiary.v1.PaymentBeneficiaryDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;
public interface PaymentBeneficiaryManagerService {
    
    Mono<PaginationResponse<PaymentBeneficiaryDTO>> getAllBeneficiaries(FilterRequest<PaymentBeneficiaryDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentBeneficiaryDTO>> getBeneficiariesByPayerAccountId(UUID payerAccountId, FilterRequest<PaymentBeneficiaryDTO> filterRequest);
    
    Mono<PaginationResponse<PaymentBeneficiaryDTO>> getFavoriteBeneficiariesByPayerAccountId(UUID payerAccountId, FilterRequest<PaymentBeneficiaryDTO> filterRequest);
    
    Mono<PaymentBeneficiaryDTO> createBeneficiary(PaymentBeneficiaryDTO paymentBeneficiaryDTO);
    
    Mono<PaymentBeneficiaryDTO> getBeneficiaryById(UUID paymentBeneficiaryId);
    
    Mono<PaymentBeneficiaryDTO> updateBeneficiary(UUID paymentBeneficiaryId, PaymentBeneficiaryDTO paymentBeneficiaryDTO);
    
    Mono<PaymentBeneficiaryDTO> markAsFavorite(UUID paymentBeneficiaryId, Boolean isFavorite);
    
    Mono<Void> deleteBeneficiary(UUID paymentBeneficiaryId);
}
