package com.firefly.core.banking.payments.interfaces.dtos.correspondence.v1;

import com.firefly.core.banking.payments.interfaces.dtos.BaseDTO;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCorrespondenceDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID paymentCorrespondenceId;

    @FilterableId
    @NotNull(message = "Payment order ID is required")
    private UUID paymentOrderId;

    @NotBlank(message = "Correspondence type is required")
    @Size(max = 50, message = "Correspondence type must not exceed 50 characters")
    private String correspondenceType;

    @NotNull(message = "Correspondence date is required")
    private LocalDateTime correspondenceDate;

    @NotBlank(message = "Correspondence channel is required")
    @Size(max = 50, message = "Correspondence channel must not exceed 50 characters")
    private String correspondenceChannel;

    @NotBlank(message = "Correspondence direction is required")
    @Pattern(regexp = "^(INBOUND|OUTBOUND)$",
             message = "Correspondence direction must be INBOUND or OUTBOUND")
    private String correspondenceDirection; // 'INBOUND' or 'OUTBOUND'

    @Size(max = 255, message = "Correspondent bank must not exceed 255 characters")
    private String correspondentBank;

    @Size(max = 100, message = "Correspondent reference must not exceed 100 characters")
    private String correspondentReference;

    @NotBlank(message = "Message content is required")
    @Size(max = 2000, message = "Message content must not exceed 2000 characters")
    private String messageContent;

    private UUID attachmentId;
}
