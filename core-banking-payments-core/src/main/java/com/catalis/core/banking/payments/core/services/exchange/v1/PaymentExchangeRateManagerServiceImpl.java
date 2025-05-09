package com.catalis.core.banking.payments.core.services.exchange.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.mappers.manager.exchange.v1.PaymentExchangeRateMapper;
import com.catalis.core.banking.payments.interfaces.dtos.exchange.v1.PaymentExchangeRateDTO;
import com.catalis.core.banking.payments.models.entities.exchange.v1.PaymentExchangeRate;
import com.catalis.core.banking.payments.models.repositories.exchange.v1.PaymentExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentExchangeRateManagerServiceImpl implements PaymentExchangeRateManagerService {

    @Autowired
    private PaymentExchangeRateRepository repository;

    @Autowired
    private PaymentExchangeRateMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentExchangeRateDTO>> getExchangeRatesByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentExchangeRateDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentExchangeRate.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentExchangeRateDTO> createExchangeRate(Long paymentOrderId, PaymentExchangeRateDTO paymentExchangeRateDTO) {
        paymentExchangeRateDTO.setPaymentOrderId(paymentOrderId);
        PaymentExchangeRate entity = mapper.toEntity(paymentExchangeRateDTO);
        return repository.save(entity).map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentExchangeRateDTO> getExchangeRateById(Long paymentExchangeRateId) {
        return repository.findById(paymentExchangeRateId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentExchangeRateDTO> updateExchangeRate(Long paymentOrderId, Long paymentExchangeRateId, PaymentExchangeRateDTO paymentExchangeRateDTO) {
        return repository.findById(paymentExchangeRateId)
                .flatMap(existingEntity -> {
                    PaymentExchangeRate updatedEntity = mapper.toEntity(paymentExchangeRateDTO);
                    updatedEntity.setPaymentExchangeRateId(existingEntity.getPaymentExchangeRateId());
                    updatedEntity.setPaymentOrderId(paymentOrderId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteExchangeRate(Long paymentExchangeRateId) {
        return repository.deleteById(paymentExchangeRateId);
    }

    @Override
    public Mono<PaymentExchangeRateDTO> getCurrentExchangeRate(String sourceCurrency, String targetCurrency) {
        // In a real implementation, this would call an external exchange rate service
        // For now, we'll return a mock exchange rate
        PaymentExchangeRateDTO mockRate = new PaymentExchangeRateDTO();
        mockRate.setSourceCurrency(sourceCurrency);
        mockRate.setTargetCurrency(targetCurrency);
        mockRate.setRate(new BigDecimal("1.1234"));
        mockRate.setRateDate(LocalDateTime.now());
        mockRate.setRateProvider("MOCK_PROVIDER");
        mockRate.setRateType("SPOT");
        
        return Mono.just(mockRate);
    }
}
