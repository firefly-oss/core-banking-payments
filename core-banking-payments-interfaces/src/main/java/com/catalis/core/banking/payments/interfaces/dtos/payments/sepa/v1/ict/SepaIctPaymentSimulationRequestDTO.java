package com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict;

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
public class SepaIctPaymentSimulationRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentMethodTypeEnum methodType = PaymentMethodTypeEnum.SEPA_ICT;

    private BigDecimal amount;
    private String currencyFrom;  // Typically "EUR"
    private String currencyTo;    // Typically "EUR"

    /**
     * Requested execution date/time. In some systems,
     * instant transfers are real-time, but you can still supply a schedule.
     */
    private LocalDateTime requestedExecutionDate;

    private CreditorDTO creditor;

    /**
     * SEPA End-to-End ID or reference (for Instant).
     */
    private String endToEndId;

    /**
     * Remittance information.
     */
    private String remittanceInformation;
}

