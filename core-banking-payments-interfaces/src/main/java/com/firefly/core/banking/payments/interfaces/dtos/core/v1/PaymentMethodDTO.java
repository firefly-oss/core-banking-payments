package com.firefly.core.banking.payments.interfaces.dtos.core.v1;

import com.firefly.core.banking.payments.interfaces.dtos.BaseDTO;
import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentPriorityEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID paymentMethodId;

    @NotBlank(message = "Method name is required")
    @Size(max = 50, message = "Method name must not exceed 50 characters")
    private String methodName;    // e.g. "SEPA_SCT", "SWIFT", "SEPA_INST"

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotNull(message = "Active flag is required")
    private Boolean activeFlag;

    @Min(value = 0, message = "Processing time hours must be non-negative")
    @Max(value = 8760, message = "Processing time hours must not exceed 8760 (1 year)")
    private Integer processingTimeHours;

    @DecimalMin(value = "0.00", message = "Minimum amount must be non-negative")
    @Digits(integer = 16, fraction = 2, message = "Minimum amount must have at most 16 integer digits and 2 decimal places")
    private BigDecimal minAmount;

    @DecimalMin(value = "0.00", message = "Maximum amount must be non-negative")
    @Digits(integer = 16, fraction = 2, message = "Maximum amount must have at most 16 integer digits and 2 decimal places")
    private BigDecimal maxAmount;

    @NotEmpty(message = "Supported currencies are required")
    @Size(min = 1, max = 10, message = "Must support between 1 and 10 currencies")
    private String[] supportedCurrencies;

    @NotNull(message = "Requires intermediary bank flag is required")
    private Boolean requiresIntermediaryBank;

    @NotNull(message = "Requires regulatory reporting flag is required")
    private Boolean requiresRegulatoryReporting;

    @NotNull(message = "Requires purpose code flag is required")
    private Boolean requiresPurposeCode;

    private PaymentPriorityEnum defaultPriority;
}
