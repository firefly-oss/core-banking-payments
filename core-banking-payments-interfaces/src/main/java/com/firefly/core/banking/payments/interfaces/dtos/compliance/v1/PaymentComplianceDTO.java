package com.firefly.core.banking.payments.interfaces.dtos.compliance.v1;

import com.firefly.core.banking.payments.interfaces.dtos.BaseDTO;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentComplianceDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentComplianceId;

    @FilterableId
    private Long paymentOrderId;

    private String screeningStatus;
    private LocalDateTime screeningDate;
    private String screeningReference;
    private Integer riskScore;
    private String riskLevel;
    private String complianceNotes;
    private String amlCheckStatus;
    private String sanctionsCheckStatus;
    private String pepCheckStatus;
    private String kycCheckStatus;
    private String approvedBy;
    private LocalDateTime approvalDate;
    private String rejectionReason;
}
