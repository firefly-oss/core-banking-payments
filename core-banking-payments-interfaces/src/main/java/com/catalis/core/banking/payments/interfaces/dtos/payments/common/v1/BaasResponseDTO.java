package com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1;

import lombok.*;

/**
 * DTO to store raw or processed information from a Banking-as-a-Service (BaaS) provider.
 * This can include status codes, messages, or raw JSON responses from the BaaS API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaasResponseDTO {

    /**
     * A status or code returned by the BaaS provider (e.g., "OK", "ERROR", "PENDING").
     */
    private String statusCode;

    /**
     * A raw or processed message from the BaaS provider,
     * potentially containing more detailed information.
     */
    private String message;

    /**
     * A place to store raw JSON or XML, to keep
     * the entire response from the BaaS service for troubleshooting.
     */
    private String rawResponse;

    /**
     * An optional reference or correlation ID from the BaaS system.
     */
    private String correlationId;
}
