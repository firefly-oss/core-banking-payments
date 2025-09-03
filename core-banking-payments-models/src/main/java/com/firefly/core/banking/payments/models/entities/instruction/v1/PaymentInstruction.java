package com.firefly.core.banking.payments.models.entities.instruction.v1;

import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentPriorityEnum;
import com.firefly.core.banking.payments.interfaces.enums.payment.v1.PaymentStatusEnum;
import com.firefly.core.banking.payments.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_instruction")
public class PaymentInstruction extends BaseEntity {

    @Id
    @Column("payment_instruction_id")
    private UUID paymentInstructionId;

    @Column("payment_order_id")
    private UUID paymentOrderId;

    @Column("instruction_id")
    private String instructionId;

    @Column("instruction_type")
    private String instructionType;

    @Column("instruction_date")
    private java.time.LocalDateTime instructionDate;

    @Column("instruction_status")
    private PaymentStatusEnum instructionStatus;

    @Column("external_reference")
    private String externalReference;

    @Column("instruction_priority")
    private PaymentPriorityEnum instructionPriority;

    @Column("instruction_notes")
    private String instructionNotes;

    @Column("retry_count")
    private Integer retryCount;

    @Column("last_retry_date")
    private java.time.LocalDateTime lastRetryDate;

    @Column("error_code")
    private String errorCode;

    @Column("error_description")
    private String errorDescription;
}
