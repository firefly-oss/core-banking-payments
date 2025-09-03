package com.firefly.core.banking.payments.interfaces.dtos.compliance.v1;

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
public class PaymentComplianceDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID paymentComplianceId;

    @FilterableId
    @NotNull(message = "Payment order ID is required")
    private UUID paymentOrderId;

    @NotBlank(message = "Screening status is required")
    @Pattern(regexp = "^(PENDING|APPROVED|REJECTED|IN_PROGRESS)$",
             message = "Screening status must be PENDING, APPROVED, REJECTED, or IN_PROGRESS")
    private String screeningStatus;

    private LocalDateTime screeningDate;

    @Size(max = 100, message = "Screening reference must not exceed 100 characters")
    private String screeningReference;

    @Min(value = 0, message = "Risk score must be non-negative")
    @Max(value = 100, message = "Risk score must not exceed 100")
    private Integer riskScore;

    @Pattern(regexp = "^(LOW|MEDIUM|HIGH|CRITICAL)$",
             message = "Risk level must be LOW, MEDIUM, HIGH, or CRITICAL")
    private String riskLevel;

    @Size(max = 1000, message = "Compliance notes must not exceed 1000 characters")
    private String complianceNotes;

    @Pattern(regexp = "^(PASS|FAIL|PENDING|NOT_APPLICABLE)$",
             message = "AML check status must be PASS, FAIL, PENDING, or NOT_APPLICABLE")
    private String amlCheckStatus;

    @Pattern(regexp = "^(PASS|FAIL|PENDING|NOT_APPLICABLE)$",
             message = "Sanctions check status must be PASS, FAIL, PENDING, or NOT_APPLICABLE")
    private String sanctionsCheckStatus;

    @Pattern(regexp = "^(PASS|FAIL|PENDING|NOT_APPLICABLE)$",
             message = "PEP check status must be PASS, FAIL, PENDING, or NOT_APPLICABLE")
    private String pepCheckStatus;

    @Pattern(regexp = "^(PASS|FAIL|PENDING|NOT_APPLICABLE)$",
             message = "KYC check status must be PASS, FAIL, PENDING, or NOT_APPLICABLE")
    private String kycCheckStatus;

    @Size(max = 255, message = "Approved by must not exceed 255 characters")
    private String approvedBy;

    private LocalDateTime approvalDate;

    @Size(max = 500, message = "Rejection reason must not exceed 500 characters")
    private String rejectionReason;
}
