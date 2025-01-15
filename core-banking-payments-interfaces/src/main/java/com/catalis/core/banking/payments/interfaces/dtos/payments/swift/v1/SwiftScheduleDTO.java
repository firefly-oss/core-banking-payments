package com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * DTO for scheduling a recurring or delayed SWIFT transfer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwiftScheduleDTO {

    private String swiftCode;
    private String abaRoutingNumber;
    private String bic;
    private String accountNumberOrIban;

    private BigDecimal amount;
    private String currency;
    private String reference;

    private LocalDateTime startDateTime;
    private Period frequency;
    private int totalOccurrences;
}

