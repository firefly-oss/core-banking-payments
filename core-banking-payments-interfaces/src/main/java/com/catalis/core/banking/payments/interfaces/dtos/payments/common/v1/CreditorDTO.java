package com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1;

import lombok.*;

/**
 * DTO representing the creditor (payee) in a payment transaction.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditorDTO {

    /**
     * The name of the creditor (individual or organization).
     */
    private String name;

    /**
     * The unique account ID (internal to your system) of the creditor.
     */
    private String accountId;

    /**
     * IBAN of the creditor's account, if applicable.
     */
    private String iban;

    /**
     * Additional details about the creditor, if needed.
     */
    private String details;
}
