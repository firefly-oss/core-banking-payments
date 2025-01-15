package com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for simulating a SWIFT transfer,
 * including optional ABA routing for US-based transactions.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwiftSimulationDTO {

    private String swiftCode;
    private String abaRoutingNumber;   // needed for US-based banks
    private String bic;
    private String accountNumberOrIban;

    private BigDecimal amount;
    private String currency;
    private String reference;

    private LocalDateTime intendedExecutionDateTime;
    private boolean triggerOtp;
}

