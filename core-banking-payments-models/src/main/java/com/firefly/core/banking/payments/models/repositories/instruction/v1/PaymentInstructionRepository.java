package com.firefly.core.banking.payments.models.repositories.instruction.v1;

import com.firefly.core.banking.payments.models.entities.instruction.v1.PaymentInstruction;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;

import java.util.UUID;
public interface PaymentInstructionRepository extends BaseRepository<PaymentInstruction, UUID> {
}
