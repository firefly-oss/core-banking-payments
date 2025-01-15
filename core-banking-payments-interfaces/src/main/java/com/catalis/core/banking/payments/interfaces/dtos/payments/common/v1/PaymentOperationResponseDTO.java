package com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1;

import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentOperationTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * A universal response DTO to indicate the result of a payment operation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOperationResponseDTO {

    /**
     * The method type: SEPA_SCT, SEPA_DIRECT_DEBIT, or SWIFT.
     */
    private PaymentMethodTypeEnum methodType;

    /**
     * The operation type: SIMULATION, EXECUTION, SCHEDULE, CANCEL.
     */
    private PaymentOperationTypeEnum operationType;

    /**
     * A unique reference or ID for this operation (optional).
     */
    private String operationReference;

    /**
     * Overall status of the operation (e.g., "SUCCESS", "FAILED").
     */
    private String status;

    /**
     * A human-readable message or description of the result.
     */
    private String message;

    /**
     * Original response from the BaaS
     */
    private String baasResponse;

    /**
     * A reference number used to track the OTP session if an OTP was triggered.
     * This value is used to match the user-provided OTP against the correct operation.
     */
    private String otpReference;

    /**
     * Timestamp indicating when the response was generated.
     */
    private LocalDateTime timestamp;
}