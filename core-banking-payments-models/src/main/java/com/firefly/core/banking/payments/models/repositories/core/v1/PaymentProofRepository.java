package com.firefly.core.banking.payments.models.repositories.core.v1;

import com.firefly.core.banking.payments.models.entities.core.v1.PaymentProof;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;

import java.util.UUID;
public interface PaymentProofRepository extends BaseRepository<PaymentProof, UUID> {
}