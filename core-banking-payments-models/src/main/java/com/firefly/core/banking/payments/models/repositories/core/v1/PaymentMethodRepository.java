package com.firefly.core.banking.payments.models.repositories.core.v1;

import com.firefly.core.banking.payments.models.entities.core.v1.PaymentMethod;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;

import java.util.UUID;
public interface PaymentMethodRepository extends BaseRepository<PaymentMethod, UUID> {
}