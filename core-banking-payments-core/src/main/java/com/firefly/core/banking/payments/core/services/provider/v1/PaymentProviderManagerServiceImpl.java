package com.firefly.core.banking.payments.core.services.provider.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.provider.v1.PaymentProviderMapper;
import com.firefly.core.banking.payments.interfaces.dtos.provider.v1.PaymentProviderDTO;
import com.firefly.core.banking.payments.models.entities.provider.v1.PaymentProvider;
import com.firefly.core.banking.payments.models.repositories.provider.v1.PaymentProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Service
@Transactional
public class PaymentProviderManagerServiceImpl implements PaymentProviderManagerService {

    @Autowired
    private PaymentProviderRepository repository;

    @Autowired
    private PaymentProviderMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentProviderDTO>> getAllPaymentProviders(UUID paymentOrderId, FilterRequest<PaymentProviderDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentProvider.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentProviderDTO> createPaymentProvider(UUID paymentOrderId, PaymentProviderDTO paymentProviderDTO) {
        PaymentProvider paymentProvider = mapper.toEntity(paymentProviderDTO);
        paymentProvider.setPaymentOrderId(paymentOrderId);
        return repository.save(paymentProvider)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentProviderDTO> getPaymentProviderById(UUID paymentOrderId, UUID paymentProviderId) {
        return repository.findById(paymentProviderId)
                .filter(paymentProvider -> paymentProvider.getPaymentOrderId().equals(paymentOrderId))
                .map(mapper::toDTO)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Payment provider not found for the given ID and payment order.")));
    }

    @Override
    public Mono<PaymentProviderDTO> updatePaymentProvider(UUID paymentOrderId, UUID paymentProviderId, PaymentProviderDTO paymentProviderDTO) {
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
    public Mono<Void> deletePaymentProvider(UUID paymentOrderId, UUID paymentProviderId) {
        return repository.findById(paymentProviderId)
                .filter(paymentProvider -> paymentProvider.getPaymentOrderId().equals(paymentOrderId))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Payment provider not found for deletion.")))
                .flatMap(repository::delete);
    }
}