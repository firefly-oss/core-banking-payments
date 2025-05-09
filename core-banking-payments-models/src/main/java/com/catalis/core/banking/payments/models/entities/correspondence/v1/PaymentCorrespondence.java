package com.catalis.core.banking.payments.models.entities.correspondence.v1;

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
@Table("payment_correspondence")
public class PaymentCorrespondence extends BaseEntity {

    @Id
    @Column("payment_correspondence_id")
    private Long paymentCorrespondenceId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("correspondence_type")
    private String correspondenceType;

    @Column("correspondence_date")
    private LocalDateTime correspondenceDate;

    @Column("correspondence_channel")
    private String correspondenceChannel;

    @Column("correspondence_direction")
    private String correspondenceDirection; // 'INBOUND' or 'OUTBOUND'

    @Column("correspondent_bank")
    private String correspondentBank;

    @Column("correspondent_reference")
    private String correspondentReference;

    @Column("message_content")
    private String messageContent;

    @Column("attachment_id")
    private Long attachmentId;
}
