package com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1;

public enum PaymentTypeEnum {
    SEPA_SCT,          // SEPA Credit Transfer
    SEPA_ICT,          // SEPA instant
    SEPA_DIRECT_DEBIT, // For direct debit mandates
    INTERNAL,          // INSIDE US
    SWIFT,             // WIRE / SWIFT
    PAYROLL            // Payrolls
}