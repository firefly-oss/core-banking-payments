/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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
