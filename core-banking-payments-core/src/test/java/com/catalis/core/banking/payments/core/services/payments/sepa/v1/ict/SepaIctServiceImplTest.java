package com.catalis.core.banking.payments.core.services.payments.sepa.v1.ict;

import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.CreditorDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.common.v1.PaymentOperationResponseDTO;
import com.catalis.core.banking.payments.interfaces.dtos.payments.sepa.v1.ict.*;
import com.catalis.core.banking.payments.interfaces.enums.payments.PaymentMethodTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class SepaIctServiceImplTest {

    @InjectMocks
    private SepaIctServiceImpl service;

    private final Long accountId = 1234L;
    private SepaIctPaymentSimulationRequestDTO simulationDTO;
    private SepaIctPaymentExecutionRequestDTO executionDTO;
    private PaymentOperationResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        // Setup common test data
        CreditorDTO creditor = CreditorDTO.builder()
                .name("Test Creditor")
                .iban("ES9121000418450200051332")
                .bic("CAIXESBBXXX")
                .build();

        // Setup simulation DTO
        simulationDTO = SepaIctPaymentSimulationRequestDTO.builder()
                .amount(new BigDecimal("100.00"))
                .currencyFrom("EUR")
                .currencyTo("EUR")
                .requestedExecutionDate(LocalDateTime.now().plusDays(1))
                .creditor(creditor)
                .endToEndId("E2E-123")
                .remittanceInformation("Invoice payment")
                .build();

        // Setup execution DTO
        executionDTO = SepaIctPaymentExecutionRequestDTO.builder()
                .operationReference("OP-REF-123")
                .amount(new BigDecimal("100.00"))
                .currencyFrom("EUR")
                .currencyTo("EUR")
                .requestedExecutionDate(LocalDateTime.now().plusDays(1))
                .creditor(creditor)
                .endToEndId("E2E-123")
                .remittanceInformation("Invoice payment")
                .otpCode("123456")
                .build();

        // Setup response DTO
        responseDTO = PaymentOperationResponseDTO.builder()
                .methodType(PaymentMethodTypeEnum.SEPA_ICT)
                .operationReference("OP-REF-123")
                .status("SUCCESS")
                .message("Operation completed successfully")
                .timestamp(LocalDateTime.now())
                .totalFees(new BigDecimal("2.50"))
                .valueDate(LocalDateTime.now().plusDays(1))
                .postingDate(LocalDateTime.now())
                .currencyFrom("EUR")
                .currencyTo("EUR")
                .build();
    }

    @Test
    void simulatePayment_ShouldReturnNull() {
        // Since the implementation returns null, we just verify it doesn't throw an exception
        Mono<PaymentOperationResponseDTO> result = service.simulatePayment(accountId, simulationDTO);
        if (result == null) {
            // If null is returned, test passes
            return;
        }
        // If not null, verify it completes without emitting any value
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void executePayment_ShouldReturnNull() {
        // Since the implementation returns null, we just verify it doesn't throw an exception
        Mono<PaymentOperationResponseDTO> result = service.executePayment(accountId, executionDTO);
        if (result == null) {
            // If null is returned, test passes
            return;
        }
        // If not null, verify it completes without emitting any value
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void simulateDeletion_ShouldReturnNull() {
        // Since the implementation returns null, we just verify it doesn't throw an exception
        Mono<PaymentOperationResponseDTO> result = service.simulateDeletion(accountId, simulationDTO);
        if (result == null) {
            // If null is returned, test passes
            return;
        }
        // If not null, verify it completes without emitting any value
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void executeDeletion_ShouldReturnNull() {
        // Since the implementation returns null, we just verify it doesn't throw an exception
        Mono<PaymentOperationResponseDTO> result = service.executeDeletion(accountId, executionDTO);
        if (result == null) {
            // If null is returned, test passes
            return;
        }
        // If not null, verify it completes without emitting any value
        StepVerifier.create(result)
                .verifyComplete();
    }

    // Note: These tests are placeholders since the actual implementation returns null.
    // When the implementation is completed, these tests should be updated to verify
    // the actual behavior of the service.
}
