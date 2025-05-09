package com.catalis.core.banking.payments.interfaces.dtos.correspondence.v1;

import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
import com.catalis.core.utils.annotations.FilterableId;
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
public class PaymentCorrespondenceDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentCorrespondenceId;

    @FilterableId
    private Long paymentOrderId;

    private String correspondenceType;
    private LocalDateTime correspondenceDate;
    private String correspondenceChannel;
    private String correspondenceDirection; // 'INBOUND' or 'OUTBOUND'
    private String correspondentBank;
    private String correspondentReference;
    private String messageContent;
    private Long attachmentId;
}
