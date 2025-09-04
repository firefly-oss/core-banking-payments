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


package com.firefly.core.banking.payments.core.services.core.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.core.v1.PaymentMethodMapper;
import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentMethodDTO;
import com.firefly.core.banking.payments.models.entities.core.v1.PaymentMethod;
import com.firefly.core.banking.payments.models.repositories.core.v1.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Service
@Transactional
public class PaymentMethodManagerServiceImpl implements PaymentMethodManagerService {

    @Autowired
    private PaymentMethodRepository repository;

    @Autowired
    private PaymentMethodMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentMethodDTO>> getAllPaymentMethods(FilterRequest<PaymentMethodDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentMethod.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentMethodDTO> createPaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod entity = mapper.toEntity(paymentMethodDTO);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentMethodDTO> getPaymentMethodById(UUID paymentMethodId) {
        return repository.findById(paymentMethodId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentMethodDTO> updatePaymentMethod(UUID paymentMethodId, PaymentMethodDTO paymentMethodDTO) {
        return repository.findById(paymentMethodId)
                .flatMap(existingEntity -> {
                    existingEntity.setMethodName(paymentMethodDTO.getMethodName());
                    existingEntity.setDescription(paymentMethodDTO.getDescription());
                    existingEntity.setActiveFlag(paymentMethodDTO.getActiveFlag());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePaymentMethod(UUID paymentMethodId) {
        return repository.findById(paymentMethodId)
                .flatMap(repository::delete);
    }
}