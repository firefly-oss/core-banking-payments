package com.firefly.core.banking.payments.core.services.compliance.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.compliance.v1.PaymentComplianceMapper;
import com.firefly.core.banking.payments.interfaces.dtos.compliance.v1.PaymentComplianceDTO;
import com.firefly.core.banking.payments.models.entities.compliance.v1.PaymentCompliance;
import com.firefly.core.banking.payments.models.repositories.compliance.v1.PaymentComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentComplianceManagerServiceImpl implements PaymentComplianceManagerService {

    @Autowired
    private PaymentComplianceRepository repository;

    @Autowired
    private PaymentComplianceMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentComplianceDTO>> getComplianceByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentComplianceDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentCompliance.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentComplianceDTO> createCompliance(Long paymentOrderId, PaymentComplianceDTO paymentComplianceDTO) {
        paymentComplianceDTO.setPaymentOrderId(paymentOrderId);
        PaymentCompliance entity = mapper.toEntity(paymentComplianceDTO);
        return repository.save(entity).map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentComplianceDTO> getComplianceById(Long paymentComplianceId) {
        return repository.findById(paymentComplianceId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentComplianceDTO> updateCompliance(Long paymentOrderId, Long paymentComplianceId, PaymentComplianceDTO paymentComplianceDTO) {
        return repository.findById(paymentComplianceId)
                .flatMap(existingEntity -> {
                    PaymentCompliance updatedEntity = mapper.toEntity(paymentComplianceDTO);
                    updatedEntity.setPaymentComplianceId(existingEntity.getPaymentComplianceId());
                    updatedEntity.setPaymentOrderId(paymentOrderId);
                    updatedEntity.setDateCreated(existingEntity.getDateCreated());
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentComplianceDTO> approveCompliance(Long paymentOrderId, String approvedBy, String complianceNotes) {
        return repository.findFirstByPaymentOrderIdOrderByDateCreatedDesc(paymentOrderId)
                .flatMap(existingEntity -> {
                    existingEntity.setApprovedBy(approvedBy);
                    existingEntity.setComplianceNotes(complianceNotes);
                    existingEntity.setApprovalDate(LocalDateTime.now());
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentComplianceDTO> rejectCompliance(Long paymentOrderId, String rejectionReason) {
        return repository.findFirstByPaymentOrderIdOrderByDateCreatedDesc(paymentOrderId)
                .flatMap(existingEntity -> {
                    existingEntity.setRejectionReason(rejectionReason);
                    return repository.save(existingEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCompliance(Long paymentComplianceId) {
        return repository.deleteById(paymentComplianceId);
    }
}
