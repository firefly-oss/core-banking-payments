package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.CreditorDTO;
import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Execution, cancel, schedule, or periodic setup for a SEPA Instant Credit Transfer (ICT).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SepaIctPaymentExecutionRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentMethodTypeEnum methodType = PaymentMethodTypeEnum.SEPA_ICT;

    private String operationReference;

    private BigDecimal amount;
    private String currencyFrom;
    private String currencyTo;

    private LocalDateTime requestedExecutionDate;

    private CreditorDTO creditor;

    private String endToEndId;
    private String remittanceInformation;

    private String otpCode;

}

