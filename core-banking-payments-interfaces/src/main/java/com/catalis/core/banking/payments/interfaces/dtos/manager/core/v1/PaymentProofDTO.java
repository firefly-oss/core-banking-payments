package com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1;

import com.catalis.common.core.filters.FilterableId;
import com.catalis.core.banking.payments.interfaces.dtos.BaseDTO;
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
public class PaymentProofDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentProofId;

    @FilterableId
    private Long paymentOrderId;

    @FilterableId
    private Long documentId;

    // e.g. "Audit Trail", "Certificate"
    private String proofType;

    private LocalDateTime proofDate;
}
