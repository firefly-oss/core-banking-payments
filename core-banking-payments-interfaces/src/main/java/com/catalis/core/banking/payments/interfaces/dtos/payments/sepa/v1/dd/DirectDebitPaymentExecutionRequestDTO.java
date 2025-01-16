package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.dd;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.CreditorDTO;
import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Execution, cancel, schedule, or periodic setup for a SEPA Direct Debit.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DirectDebitPaymentExecutionRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentMethodTypeEnum methodType = PaymentMethodTypeEnum.SEPA_DIRECT_DEBIT;

    private String operationReference;

    private BigDecimal amount;
    private String currencyFrom;
    private String currencyTo;

    private LocalDateTime requestedExecutionDate;

    private String mandateId;
    private LocalDate mandateSignatureDate;

    private CreditorDTO creditor;

    private String mandateDescription;

    private String otpCode;
}
