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
import com.firefly.core.banking.payments.core.mappers.core.v1.PaymentFeeMapper;
import com.firefly.core.banking.payments.interfaces.dtos.core.v1.PaymentFeeDTO;
import com.firefly.core.banking.payments.models.entities.core.v1.PaymentFee;
import com.firefly.core.banking.payments.models.repositories.core.v1.PaymentFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Service
@Transactional
public class PaymentFeeManagerServiceImpl implements PaymentFeeManagerService {

    @Autowired
    private PaymentFeeRepository repository;

    @Autowired
    private PaymentFeeMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentFeeDTO>> getAllPaymentFees(UUID paymentOrderId, FilterRequest<PaymentFeeDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentFee.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentFeeDTO> createPaymentFee(UUID paymentOrderId, PaymentFeeDTO paymentFeeDTO) {
        paymentFeeDTO.setPaymentOrderId(paymentOrderId);
        PaymentFee entity = mapper.toEntity(paymentFeeDTO);
        return repository.save(entity).map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentFeeDTO> getPaymentFeeById(UUID paymentOrderId, UUID paymentFeeId) {
        return repository.findById(paymentFeeId)
                .filter(paymentFee -> paymentFee.getPaymentOrderId().equals(paymentOrderId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentFeeDTO> updatePaymentFee(UUID paymentOrderId, UUID paymentFeeId, PaymentFeeDTO paymentFeeDTO) {
        return repository.findById(paymentFeeId)
                .filter(paymentFee -> paymentFee.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(existingPaymentFee -> {
                    PaymentFee updatedEntity = mapper.toEntity(paymentFeeDTO);
                    updatedEntity.setPaymentFeeId(paymentFeeId);
                    updatedEntity.setPaymentOrderId(paymentOrderId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePaymentFee(UUID paymentOrderId, UUID paymentFeeId) {
        return repository.findById(paymentFeeId)
                .filter(paymentFee -> paymentFee.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(repository::delete);
    }
}