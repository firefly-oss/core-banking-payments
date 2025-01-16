package com.catalis.core.banking.payments.interfaces.dtos.payments.swift.v1;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.CreditorDTO;
import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SwiftPaymentSimulationRequestDTO {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentMethodTypeEnum methodType = PaymentMethodTypeEnum.SWIFT;

    private BigDecimal amount;

    /**
     * Could be "EUR" -> "USD", or "EUR" -> "GBP", etc.
     */
    private String currencyFrom;
    private String currencyTo;

    /**
     * Requested execution date (for schedule or simulation of schedule).
     */
    private LocalDateTime requestedExecutionDate;

    private CreditorDTO creditor;

    /**
     * SWIFT / BIC code of the beneficiary bank.
     * (Although we also have it in CreditorDTO,
     * it’s sometimes repeated at this level for clarity.)
     */
    private String bic;

    /**
     * ABA Number (if the target bank is in the USA).
     */
    private String abaNumber;

    /**
     * The beneficiary’s full address (if required for compliance).
     * Partly duplicated with CreditorDTO’s address, but
     * in some flows you store it separately for the SWIFT message.
     */
    private String beneficiaryAddressLine;
    private String beneficiaryCity;
    private String beneficiaryProvince;
    private String beneficiaryPostalCode;
    private String beneficiaryCountry;

    /**
     * If an intermediary bank is used, specify the details here.
     */
    private String intermediaryBankBic;
    private String intermediaryBankName;

    /**
     * Payment reference or concept for SWIFT instructions.
     */
    private String paymentDetails;
}