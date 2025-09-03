package com.firefly.core.banking.payments.interfaces.dtos.core.v1;

import com.firefly.core.banking.payments.interfaces.dtos.BaseDTO;
import com.firefly.core.banking.payments.interfaces.enums.fee.v1.FeeTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
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
public class PaymentFeeDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID paymentFeeId;

    @FilterableId
    @NotNull(message = "Payment order ID is required")
    private UUID paymentOrderId;

    @Size(max = 50, message = "Fee type must not exceed 50 characters")
    private String feeType;

    @NotNull(message = "Fee type enum is required")
    private FeeTypeEnum feeTypeEnum;

    @NotNull(message = "Fee amount is required")
    @DecimalMin(value = "0.00", message = "Fee amount must be non-negative")
    @Digits(integer = 16, fraction = 2, message = "Fee amount must have at most 16 integer digits and 2 decimal places")
    private BigDecimal feeAmount;

    @NotBlank(message = "Fee currency code is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Fee currency code must be a valid 3-letter ISO code")
    private String feeCurrencyCode;

    @Size(max = 255, message = "Fee description must not exceed 255 characters")
    private String feeDescription;

    @Size(max = 100, message = "Fee calculation method must not exceed 100 characters")
    private String feeCalculationMethod;

    @DecimalMin(value = "0.00", message = "Fee rate must be non-negative")
    @DecimalMax(value = "100.00", message = "Fee rate must not exceed 100%")
    @Digits(integer = 3, fraction = 4, message = "Fee rate must have at most 3 integer digits and 4 decimal places")
    private BigDecimal feeRate;

    @DecimalMin(value = "0.00", message = "Fee tax amount must be non-negative")
    @Digits(integer = 16, fraction = 2, message = "Fee tax amount must have at most 16 integer digits and 2 decimal places")
    private BigDecimal feeTaxAmount;

    @DecimalMin(value = "0.00", message = "Fee tax rate must be non-negative")
    @DecimalMax(value = "100.00", message = "Fee tax rate must not exceed 100%")
    @Digits(integer = 3, fraction = 4, message = "Fee tax rate must have at most 3 integer digits and 4 decimal places")
    private BigDecimal feeTaxRate;

    @Size(max = 50, message = "Fee tax type must not exceed 50 characters")
    private String feeTaxType;

    @NotNull(message = "Fee waived flag is required")
    private Boolean feeWaived;
}
