package com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1;

import lombok.*;

/**
 * DTO representing the debtor (payer) in a payment transaction.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebtorDTO {

    /**
     * The name of the debtor (individual or organization).
     */
    private String name;

    /**
     * The unique account ID (internal to your system) of the debtor.
     */
    private String accountId;

    /**
     * IBAN of the debtor's account, if applicable.
     */
    private String iban;

    /**
     * Additional details about the debtor, if needed.
     */
    private String details;
}
