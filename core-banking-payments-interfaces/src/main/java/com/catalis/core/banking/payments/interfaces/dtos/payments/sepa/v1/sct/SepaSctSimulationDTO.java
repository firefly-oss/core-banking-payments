package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for simulating a SEPA SCT (Standard Credit Transfer).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SepaSctSimulationDTO {

    private String iban;                   // IBAN for the debit or credit
    private String bic;                    // Bank Identifier Code for SEPA
    private BigDecimal amount;
    private String currency;               // e.g. "EUR"
    private String reference;              // A reference for the payment
    private LocalDateTime intendedDate;    // Proposed execution date

    /**
     * Whether to automatically trigger an OTP request
     * after a successful simulation.
     */
    private boolean triggerOtp;
}