package com.catalis.core.banking.payments.core.services.manager.provider.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.mappers.manager.provider.v1.PaymentProviderMapper;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentProviderDTO;
import com.catalis.core.banking.payments.models.entities.manager.provider.v1.PaymentProvider;
import com.catalis.core.banking.payments.models.repositories.manager.provider.v1.PaymentProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PaymentProviderManagerServiceImpl implements PaymentProviderManagerService {

    @Autowired
    private PaymentProviderRepository repository;

    @Autowired
    private PaymentProviderMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentProviderDTO>> getAllPaymentProviders(Long paymentOrderId, FilterRequest<PaymentProviderDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentProvider.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentProviderDTO> createPaymentProvider(Long paymentOrderId, PaymentProviderDTO paymentProviderDTO) {
        PaymentProvider paymentProvider = mapper.toEntity(paymentProviderDTO);
        paymentProvider.setPaymentOrderId(paymentOrderId);
        return repository.save(paymentProvider)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentProviderDTO> getPaymentProviderById(Long paymentOrderId, Long paymentProviderId) {
        return repository.findById(paymentProviderId)
                .filter(paymentProvider -> paymentProvider.getPaymentOrderId().equals(paymentOrderId))
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Payment provider not found for the given ID and payment order.")));
    }

    @Override
    public Mono<PaymentProviderDTO> updatePaymentProvider(Long paymentOrderId, Long paymentProviderId, PaymentProviderDTO paymentProviderDTO) {
        return repository.findById(paymentProviderId)
                .filter(existing -> existing.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(existing -> {
                    PaymentProvider updatedEntity = mapper.toEntity(paymentProviderDTO);
                    updatedEntity.setPaymentProviderId(paymentProviderId);
                    updatedEntity.setPaymentOrderId(paymentOrderId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Payment provider not found for update.")));
    }

    @Override
    public Mono<Void> deletePaymentProvider(Long paymentOrderId, Long paymentProviderId) {
        return repository.findById(paymentProviderId)
                .filter(paymentProvider -> paymentProvider.getPaymentOrderId().equals(paymentOrderId))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Payment provider not found for deletion.")))
                .flatMap(repository::delete);
    }
}