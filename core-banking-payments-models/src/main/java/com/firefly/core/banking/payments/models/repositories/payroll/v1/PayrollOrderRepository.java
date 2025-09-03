package com.firefly.core.banking.payments.models.repositories.payroll.v1;

import com.firefly.core.banking.payments.models.entities.payroll.v1.PayrollOrder;
import com.firefly.core.banking.payments.models.repositories.BaseRepository;

import java.util.UUID;
public interface PayrollOrderRepository extends BaseRepository<PayrollOrder, UUID> {
}
