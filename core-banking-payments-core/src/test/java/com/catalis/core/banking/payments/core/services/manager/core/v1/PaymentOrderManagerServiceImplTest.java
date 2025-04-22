package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.mappers.manager.core.v1.PaymentOrderMapper;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentOrderDTO;
import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentStatusEnum;
import com.catalis.core.banking.payments.interfaces.enums.manager.payment.v1.PaymentTypeEnum;
import com.catalis.core.banking.payments.models.entities.manager.core.v1.PaymentOrder;
import com.catalis.core.banking.payments.models.repositories.manager.core.v1.PaymentOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentOrderManagerServiceImplTest {

    @Mock
    private PaymentOrderRepository repository;

    @Mock
    private PaymentOrderMapper mapper;

    @InjectMocks
    private PaymentOrderManagerServiceImpl service;

    private PaymentOrderDTO paymentOrderDTO;
    private PaymentOrder paymentOrder;
    private final Long paymentOrderId = 1L;

    @BeforeEach
    void setUp() {
        // Setup test data
        paymentOrderDTO = PaymentOrderDTO.builder()
                .paymentOrderId(paymentOrderId)
                .payerAccountId(100L)
                .paymentMethodId(200L)
                .beneficiaryName("John Doe")
                .beneficiaryAccountNumber("123456789")
                .beneficiaryIban("ES9121000418450200051332")
                .beneficiaryBic("CAIXESBBXXX")
                .beneficiaryAddress("123 Main St, Barcelona, Spain")
                .beneficiaryCountryCode("ES")
                .paymentType(PaymentTypeEnum.SEPA_SCT)
                .orderDate(LocalDateTime.now())
                .status(PaymentStatusEnum.INITIATED)
                .amount(new BigDecimal("1000.00"))
                .currencyCode("EUR")
                .remittanceInformation("Invoice payment")
                .build();

        paymentOrder = new PaymentOrder();
        paymentOrder.setPaymentOrderId(paymentOrderId);
        paymentOrder.setPayerAccountId(100L);
        paymentOrder.setPaymentMethodId(200L);
        paymentOrder.setBeneficiaryName("John Doe");
        paymentOrder.setBeneficiaryAccountNumber("123456789");
        paymentOrder.setBeneficiaryIban("ES9121000418450200051332");
        paymentOrder.setBeneficiaryBic("CAIXESBBXXX");
        paymentOrder.setBeneficiaryAddress("123 Main St, Barcelona, Spain");
        paymentOrder.setBeneficiaryCountryCode("ES");
        paymentOrder.setPaymentType(PaymentTypeEnum.SEPA_SCT);
        paymentOrder.setOrderDate(LocalDateTime.now());
        paymentOrder.setStatus(PaymentStatusEnum.INITIATED);
        paymentOrder.setAmount(new BigDecimal("1000.00"));
        paymentOrder.setCurrencyCode("EUR");
        paymentOrder.setRemittanceInformation("Invoice payment");
    }

    @Test
    void createPaymentOrder_Success() {
        // Arrange
        when(mapper.toEntity(any(PaymentOrderDTO.class))).thenReturn(paymentOrder);
        when(repository.save(any(PaymentOrder.class))).thenReturn(Mono.just(paymentOrder));
        when(mapper.toDTO(any(PaymentOrder.class))).thenReturn(paymentOrderDTO);

        // Act & Assert
        StepVerifier.create(service.createPaymentOrder(paymentOrderDTO))
                .expectNext(paymentOrderDTO)
                .verifyComplete();

        verify(mapper).toEntity(paymentOrderDTO);
        verify(repository).save(paymentOrder);
        verify(mapper).toDTO(paymentOrder);
    }

    @Test
    void getPaymentOrderById_Success() {
        // Arrange
        when(repository.findById(paymentOrderId)).thenReturn(Mono.just(paymentOrder));
        when(mapper.toDTO(paymentOrder)).thenReturn(paymentOrderDTO);

        // Act & Assert
        StepVerifier.create(service.getPaymentOrderById(paymentOrderId))
                .expectNext(paymentOrderDTO)
                .verifyComplete();

        verify(repository).findById(paymentOrderId);
        verify(mapper).toDTO(paymentOrder);
    }

    @Test
    void getPaymentOrderById_NotFound() {
        // Arrange
        when(repository.findById(paymentOrderId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getPaymentOrderById(paymentOrderId))
                .expectErrorMessage("Payment order not found.")
                .verify();

        verify(repository).findById(paymentOrderId);
        verify(mapper, never()).toDTO(any(PaymentOrder.class));
    }

    @Test
    void updatePaymentOrder_Success() {
        // Arrange
        when(repository.findById(paymentOrderId)).thenReturn(Mono.just(paymentOrder));
        when(mapper.toEntity(any(PaymentOrderDTO.class))).thenReturn(paymentOrder);
        when(repository.save(any(PaymentOrder.class))).thenReturn(Mono.just(paymentOrder));
        when(mapper.toDTO(any(PaymentOrder.class))).thenReturn(paymentOrderDTO);

        // Act & Assert
        StepVerifier.create(service.updatePaymentOrder(paymentOrderId, paymentOrderDTO))
                .expectNext(paymentOrderDTO)
                .verifyComplete();

        verify(repository).findById(paymentOrderId);
        verify(mapper).toEntity(paymentOrderDTO);
        verify(repository).save(paymentOrder);
        verify(mapper).toDTO(paymentOrder);
    }

    @Test
    void updatePaymentOrder_NotFound() {
        // Arrange
        when(repository.findById(paymentOrderId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updatePaymentOrder(paymentOrderId, paymentOrderDTO))
                .expectErrorMessage("Payment order not found.")
                .verify();

        verify(repository).findById(paymentOrderId);
        verify(mapper, never()).toEntity(any(PaymentOrderDTO.class));
        verify(repository, never()).save(any(PaymentOrder.class));
    }

    @Test
    void deletePaymentOrder_Success() {
        // Arrange
        when(repository.findById(paymentOrderId)).thenReturn(Mono.just(paymentOrder));
        when(repository.delete(paymentOrder)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deletePaymentOrder(paymentOrderId))
                .verifyComplete();

        verify(repository).findById(paymentOrderId);
        verify(repository).delete(paymentOrder);
    }

    @Test
    void deletePaymentOrder_NotFound() {
        // Arrange
        when(repository.findById(paymentOrderId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deletePaymentOrder(paymentOrderId))
                .expectErrorMessage("Payment order not found.")
                .verify();

        verify(repository).findById(paymentOrderId);
        verify(repository, never()).delete(any(PaymentOrder.class));
    }

    // Note: This test is a placeholder for testing the getAllPaymentOrders method.
    // Since FilterUtils and PaginationResponse are external dependencies,
    // a more comprehensive test would require mocking these classes.
    @Test
    void getAllPaymentOrders_ShouldCallFilterUtils() {
        // This test verifies that the service method is implemented and doesn't throw exceptions
        // A more comprehensive test would mock FilterUtils.createFilter
        FilterRequest<PaymentOrderDTO> filterRequest = new FilterRequest<>();

        // Just verify that the method doesn't throw an exception
        // In a real test, we would mock FilterUtils.createFilter and verify its behavior
        service.getAllPaymentOrders(filterRequest);
    }
}
