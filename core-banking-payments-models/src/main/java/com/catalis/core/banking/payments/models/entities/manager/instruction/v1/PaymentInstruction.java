package com.catalis.core.banking.payments.models.entities.manager.instruction.v1;

import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentStatusEnum;
import com.catalis.core.banking.payments.models.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_instruction")
public class PaymentInstruction extends BaseEntity {

    @Id
    @Column("payment_instruction_id")
    private Long paymentInstructionId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("instruction_id")
    private String instructionId;

    @Column("instruction_type")
    private String instructionType;

    @Column("instruction_date")
    private java.time.LocalDateTime instructionDate;

    @Column("instruction_status")
    private PaymentStatusEnum instructionStatus;
}
