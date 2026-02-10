/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.banking.payments.core.services.exchange.v1;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.exchange.v1.PaymentExchangeRateMapper;
import com.firefly.core.banking.payments.interfaces.dtos.exchange.v1.PaymentExchangeRateDTO;
import com.firefly.core.banking.payments.models.entities.exchange.v1.PaymentExchangeRate;
import com.firefly.core.banking.payments.models.repositories.exchange.v1.PaymentExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;
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
    public Mono<PaginationResponse<PaymentExchangeRateDTO>> getExchangeRatesByPaymentOrderId(UUID paymentOrderId, FilterRequest<PaymentExchangeRateDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentExchangeRate.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentExchangeRateDTO> createExchangeRate(UUID paymentOrderId, PaymentExchangeRateDTO paymentExchangeRateDTO) {
        paymentExchangeRateDTO.setPaymentOrderId(paymentOrderId);
        PaymentExchangeRate entity = mapper.toEntity(paymentExchangeRateDTO);
        return repository.save(entity).map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentExchangeRateDTO> getExchangeRateById(UUID paymentExchangeRateId) {
        return repository.findById(paymentExchangeRateId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentExchangeRateDTO> updateExchangeRate(UUID paymentOrderId, UUID paymentExchangeRateId, PaymentExchangeRateDTO paymentExchangeRateDTO) {
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
    public Mono<Void> deleteExchangeRate(UUID paymentExchangeRateId) {
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
