package com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.CreditorDTO;
import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Execution, cancel, schedule, or periodic setup for a SWIFT international payment.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SwiftPaymentExecutionRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentMethodTypeEnum methodType = PaymentMethodTypeEnum.SWIFT;

    private String operationReference;

    private BigDecimal amount;
    private String currencyFrom;
    private String currencyTo;

    private LocalDateTime requestedExecutionDate;

    private CreditorDTO creditor;

    private String bic;
    private String abaNumber;

    private String beneficiaryAddressLine;
    private String beneficiaryCity;
    private String beneficiaryProvince;
    private String beneficiaryPostalCode;
    private String beneficiaryCountry;

    private String intermediaryBankBic;
    private String intermediaryBankName;

    private String paymentDetails;

    private String otpCode;
}