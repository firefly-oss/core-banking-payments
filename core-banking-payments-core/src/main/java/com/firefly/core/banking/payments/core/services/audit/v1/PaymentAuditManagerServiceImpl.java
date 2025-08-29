package com.firefly.core.banking.payments.core.services.audit.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.banking.payments.core.mappers.audit.v1.PaymentAuditMapper;
import com.firefly.core.banking.payments.interfaces.dtos.audit.v1.PaymentAuditDTO;
import com.firefly.core.banking.payments.models.entities.audit.v1.PaymentAudit;
import com.firefly.core.banking.payments.models.repositories.audit.v1.PaymentAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PaymentAuditManagerServiceImpl implements PaymentAuditManagerService {

    @Autowired
    private PaymentAuditRepository repository;

    @Autowired
    private PaymentAuditMapper mapper;

    @Override
    public Mono<PaginationResponse<PaymentAuditDTO>> getAuditsByPaymentOrderId(Long paymentOrderId, FilterRequest<PaymentAuditDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentAudit.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaginationResponse<PaymentAuditDTO>> getAuditsByPaymentInstructionId(Long paymentInstructionId, FilterRequest<PaymentAuditDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        PaymentAudit.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<PaymentAuditDTO> createAudit(PaymentAuditDTO paymentAuditDTO) {
        PaymentAudit entity = mapper.toEntity(paymentAuditDTO);
        return repository.save(entity).map(mapper::toDTO);
    }

    @Override
    public Mono<PaymentAuditDTO> getAuditById(Long paymentAuditId) {
        return repository.findById(paymentAuditId)
                .map(mapper::toDTO);
    }
}
