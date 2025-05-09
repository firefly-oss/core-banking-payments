package com.catalis.core.banking.payments.models.entities.compliance.v1;

import com.catalis.core.banking.payments.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_compliance")
public class PaymentCompliance extends BaseEntity {

    @Id
    @Column("payment_compliance_id")
    private Long paymentComplianceId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("screening_status")
    private String screeningStatus;

    @Column("screening_date")
    private LocalDateTime screeningDate;

    @Column("screening_reference")
    private String screeningReference;

    @Column("risk_score")
    private Integer riskScore;

    @Column("risk_level")
    private String riskLevel;

    @Column("compliance_notes")
    private String complianceNotes;

    @Column("aml_check_status")
    private String amlCheckStatus;

    @Column("sanctions_check_status")
    private String sanctionsCheckStatus;

    @Column("pep_check_status")
    private String pepCheckStatus;

    @Column("kyc_check_status")
    private String kycCheckStatus;

    @Column("approved_by")
    private String approvedBy;

    @Column("approval_date")
    private LocalDateTime approvalDate;

    @Column("rejection_reason")
    private String rejectionReason;
}
