/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.banking.payments.core.instructions;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Beneficiary, standing-order, and direct-debit decisions.
 * The money movement is a try, confirm, and cancel on {@link PaymentTccPort}.
 */
public final class PaymentInstructions {

    public enum BeneficiaryStatus {
        ACTIVE,
        INACTIVE
    }

    public enum MandateStatus {
        ACTIVE,
        SUSPENDED,
        CANCELLED
    }

    public record StandingOrder(LocalDate nextExecution, LocalDate endDate, int executions, Integer maxExecutions,
                                BigDecimal amount, String currency) {
    }

    public record DirectDebit(MandateStatus status, BigDecimal limit, String currency, BigDecimal amount) {
    }

    private PaymentInstructions() {
    }

    public static void requireActiveBeneficiary(BeneficiaryStatus status) {
        if (status != BeneficiaryStatus.ACTIVE) {
            throw new IllegalArgumentException("Inactive beneficiary cannot be paid");
        }
    }

    public static StandingOrder executeStandingOrder(StandingOrder order, LocalDate today) {
        if (order == null || today == null || order.nextExecution() == null) {
            throw new IllegalArgumentException("Standing order and value date are required");
        }
        if (order.amount() == null || order.amount().signum() <= 0) {
            throw new IllegalArgumentException("Standing order amount must be positive");
        }
        if (order.currency() == null || !order.currency().matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("Currency must be a 3-letter code");
        }
        if (today.isBefore(order.nextExecution())) {
            throw new IllegalArgumentException("Standing order is not due");
        }
        if (order.endDate() != null && today.isAfter(order.endDate())) {
            throw new IllegalArgumentException("Standing order has ended");
        }
        if (order.maxExecutions() != null && order.executions() >= order.maxExecutions()) {
            throw new IllegalArgumentException("Standing order has reached its execution count");
        }
        return new StandingOrder(order.nextExecution().plusMonths(1), order.endDate(), order.executions() + 1,
                order.maxExecutions(), order.amount(), order.currency());
    }

    public static void requireCollectable(DirectDebit debit) {
        if (debit == null || debit.status() != MandateStatus.ACTIVE) {
            throw new IllegalArgumentException("Direct debit mandate is not active");
        }
        if (debit.currency() == null || !debit.currency().matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("Currency must be a 3-letter code");
        }
        if (debit.amount() == null || debit.amount().signum() <= 0) {
            throw new IllegalArgumentException("Direct debit amount must be positive");
        }
        if (debit.limit() != null && debit.amount().compareTo(debit.limit()) > 0) {
            throw new IllegalArgumentException("Direct debit exceeds the mandate limit");
        }
    }
}
