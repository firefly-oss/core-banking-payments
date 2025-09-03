package com.firefly.core.banking.payments.models.entities.core.v1;

import com.firefly.core.banking.payments.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_proof")
public class PaymentProof extends BaseEntity {

    @Id
    @Column("payment_proof_id")
    private UUID paymentProofId;

    @Column("payment_order_id")
    private UUID paymentOrderId;

    @Column("document_id")
    private UUID documentId;

    @Column("proof_type")
    private String proofType;

    @Column("proof_date")
    private LocalDateTime proofDate;
}
