package com.catalis.core.banking.payments.models.entities.manager.core.v1;

import com.catalis.core.banking.payments.models.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_proof")
public class PaymentProof extends BaseEntity {

    @Id
    @Column("payment_proof_id")
    private Long paymentProofId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("document_id")
    private Long documentId;

    @Column("proof_type")
    private String proofType;

    @Column("proof_date")
    private LocalDateTime proofDate;
}
