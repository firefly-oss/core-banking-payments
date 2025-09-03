package com.firefly.core.banking.payments.models.repositories.provider.v1;

import com.firefly.core.banking.payments.models.entities.provider.v1.PaymentProvider;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;

import java.util.UUID;
public interface PaymentProviderRepository extends BaseRepository<PaymentProvider, UUID> {
}