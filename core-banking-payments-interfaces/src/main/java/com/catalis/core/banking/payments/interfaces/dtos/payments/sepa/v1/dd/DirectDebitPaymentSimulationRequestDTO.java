package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.CreditorDTO;
import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DirectDebitPaymentSimulationRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentMethodTypeEnum methodType = PaymentMethodTypeEnum.SEPA_DIRECT_DEBIT;

    private BigDecimal amount;
    private String currencyFrom; // Typically "EUR" in SEPA context
    private String currencyTo;   // Typically "EUR" or same as currencyFrom

    private LocalDateTime requestedExecutionDate;

    /**
     * The mandate ID for the direct debit (SEPA Mandate).
     */
    private String mandateId;

    /**
     * The date on which the mandate was signed.
     */
    private LocalDate mandateSignatureDate;

    private CreditorDTO creditor;

    /**
     * Reason or concept for the direct debit.
     */
    private String mandateDescription;

}