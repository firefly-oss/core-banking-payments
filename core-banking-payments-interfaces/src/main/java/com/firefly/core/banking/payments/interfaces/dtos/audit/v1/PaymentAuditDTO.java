package com.firefly.core.banking.payments.interfaces.dtos.audit.v1;

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
public class PaymentAuditDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentAuditId;

    @FilterableId
    private Long paymentOrderId;

    @FilterableId
    private Long paymentInstructionId;

    private String action;
    private LocalDateTime actionDate;
    private String actionBy;
    private String previousStatus;
    private String newStatus;
    private String ipAddress;
    private String userAgent;
    private String additionalInfo;
}
