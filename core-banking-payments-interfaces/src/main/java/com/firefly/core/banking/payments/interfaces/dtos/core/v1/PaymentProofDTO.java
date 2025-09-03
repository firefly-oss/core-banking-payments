package com.firefly.core.banking.payments.interfaces.dtos.core.v1;

import com.firefly.core.banking.payments.interfaces.dtos.BaseDTO;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProofDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID paymentProofId;

    @FilterableId
    @NotNull(message = "Payment order ID is required")
    private UUID paymentOrderId;

    @FilterableId
    @NotNull(message = "Document ID is required")
    private UUID documentId;

    // e.g. "Audit Trail", "Certificate"
    @NotBlank(message = "Proof type is required")
    @Size(max = 50, message = "Proof type must not exceed 50 characters")
    private String proofType;

    @NotNull(message = "Proof date is required")
    private LocalDateTime proofDate;
}
