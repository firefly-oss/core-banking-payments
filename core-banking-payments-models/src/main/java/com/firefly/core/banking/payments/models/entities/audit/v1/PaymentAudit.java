package com.firefly.core.banking.payments.models.entities.audit.v1;

import com.firefly.core.banking.payments.models.entities.BaseEntity;
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
@Table("payment_audit")
public class PaymentAudit extends BaseEntity {

    @Id
    @Column("payment_audit_id")
    private Long paymentAuditId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("payment_instruction_id")
    private Long paymentInstructionId;

    @Column("action")
    private String action;

    @Column("action_date")
    private LocalDateTime actionDate;

    @Column("action_by")
    private String actionBy;

    @Column("previous_status")
    private String previousStatus;

    @Column("new_status")
    private String newStatus;

    @Column("ip_address")
    private String ipAddress;

    @Column("user_agent")
    private String userAgent;

    @Column("additional_info")
    private String additionalInfo; // JSONB in the database
}
