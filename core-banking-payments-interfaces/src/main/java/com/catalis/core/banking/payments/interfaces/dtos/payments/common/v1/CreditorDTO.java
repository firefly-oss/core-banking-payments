package com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditorDTO {

    /**
     * Full name (person or entity).
     */
    private String name;

    /**
     * NIF/DNI/CIF for Spanish residents/entities.
     */
    private String nif;

    /**
     * Creditor’s IBAN (when dealing with SEPA or internal).
     */
    private String iban;

    /**
     * BIC/SWIFT code if applicable (e.g., for cross-border).
     */
    private String bic;

    /**
     * Street address (for compliance, KYC, etc.).
     */
    private String addressLine;

    /**
     * City or locality.
     */
    private String city;

    /**
     * Province or region.
     */
    private String province;

    /**
     * Postal code.
     */
    private String postalCode;

    /**
     * Country code in ISO 3166-1 alpha-2 format (e.g. "ES").
     */
    private String country;

    /**
     * Contact phone number.
     */
    private String phoneNumber;

    /**
     * Contact email address.
     */
    private String email;
}