package com.firefly.core.banking.payments.interfaces.dtos.audit.v1;

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
public class PaymentAuditDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID paymentAuditId;

    @FilterableId
    @NotNull(message = "Payment order ID is required")
    private UUID paymentOrderId;

    @FilterableId
    private UUID paymentInstructionId;

    @NotBlank(message = "Action is required")
    @Size(max = 100, message = "Action must not exceed 100 characters")
    private String action;

    @NotNull(message = "Action date is required")
    private LocalDateTime actionDate;

    @NotBlank(message = "Action by is required")
    @Size(max = 255, message = "Action by must not exceed 255 characters")
    private String actionBy;

    @Size(max = 50, message = "Previous status must not exceed 50 characters")
    private String previousStatus;

    @Size(max = 50, message = "New status must not exceed 50 characters")
    private String newStatus;

    @Pattern(regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$|^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$",
             message = "Invalid IP address format")
    private String ipAddress;

    @Size(max = 500, message = "User agent must not exceed 500 characters")
    private String userAgent;

    @Size(max = 1000, message = "Additional info must not exceed 1000 characters")
    private String additionalInfo;
}
