package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.sct;

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
public class SepaSctPaymentSimulationRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentMethodTypeEnum methodType = PaymentMethodTypeEnum.SEPA_SCT;

    private BigDecimal amount;
    private String currencyFrom;  // Typically "EUR" for SEPA
    private String currencyTo;    // Typically also "EUR" for SEPA

    private LocalDateTime requestedExecutionDate;

    private CreditorDTO creditor;

    /**
     * SEPA End-to-End ID or reference.
     */
    private String endToEndId;

    /**
     * Payment concept or remittance information for SEPA.
     */
    private String remittanceInformation;
}

