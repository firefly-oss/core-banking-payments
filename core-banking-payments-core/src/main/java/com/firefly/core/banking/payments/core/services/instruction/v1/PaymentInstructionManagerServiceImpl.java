package com.firefly.core.banking.payments.core.services.instruction.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.instruction.v1.PaymentInstructionMapper;
import com.firefly.core.banking.payments.interfaces.dtos.instruction.v1.PaymentInstructionDTO;
import com.firefly.core.banking.payments.models.entities.instruction.v1.PaymentInstruction;
import com.firefly.core.banking.payments.models.repositories.instruction.v1.PaymentInstructionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PaymentInstructionManagerServiceImpl implements PaymentInstructionManagerService {

    @Autowired
    private PaymentInstructionRepository repository;

    @Autowired
    private PaymentInstructionMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentInstructionDTO>> getAllPaymentInstructions(Long paymentOrderId, FilterRequest<PaymentInstructionDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentInstruction.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentInstructionDTO> createPaymentInstruction(Long paymentOrderId, PaymentInstructionDTO paymentInstructionDTO) {
        PaymentInstruction paymentInstruction = mapper.toEntity(paymentInstructionDTO);
        paymentInstruction.setPaymentOrderId(paymentOrderId);
        return repository.save(paymentInstruction)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentInstructionDTO> getPaymentInstructionById(Long paymentOrderId, Long paymentInstructionId) {
        return repository.findById(paymentInstructionId)
                .filter(paymentInstruction -> paymentInstruction.getPaymentOrderId().equals(paymentOrderId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentInstructionDTO> updatePaymentInstruction(Long paymentOrderId, Long paymentInstructionId, PaymentInstructionDTO paymentInstructionDTO) {
        return repository.findById(paymentInstructionId)
                .filter(paymentInstruction -> paymentInstruction.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(existing -> {
                    existing.setInstructionId(paymentInstructionDTO.getInstructionId());
                    existing.setInstructionType(paymentInstructionDTO.getInstructionType().name());
                    existing.setInstructionDate(paymentInstructionDTO.getInstructionDate());
                    existing.setInstructionStatus(paymentInstructionDTO.getInstructionStatus());
                    return repository.save(existing);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deletePaymentInstruction(Long paymentOrderId, Long paymentInstructionId) {
        return repository.findById(paymentInstructionId)
                .filter(paymentInstruction -> paymentInstruction.getPaymentOrderId().equals(paymentOrderId))
                .flatMap(repository::delete);
    }
}