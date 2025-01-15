package com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for executing a SWIFT transfer,
 * optionally requiring an OTP for final authorization.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwiftExecutionDTO {

    private String swiftCode;
    private String abaRoutingNumber;
    private String bic;
    private String accountNumberOrIban;

    private BigDecimal amount;
    private String currency;
    private String reference;

    /**
     * OTP for final authorization (if required).
     */
    private String otp;

    private LocalDateTime executionDateTime;
}

