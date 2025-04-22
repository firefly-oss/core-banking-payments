package com.catalis.core.banking.payments.core.services.manager.core.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.banking.payments.core.mappers.manager.core.v1.PaymentFeeMapper;
import com.catalis.core.banking.payments.interfaces.dtos.manager.core.v1.PaymentFeeDTO;
import com.catalis.core.banking.payments.models.entities.manager.core.v1.PaymentFee;
import com.catalis.core.banking.payments.models.repositories.manager.core.v1.PaymentFeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentFeeManagerServiceImplTest {

    @Mock
    private PaymentFeeRepository repository;

    @Mock
    private PaymentFeeMapper mapper;

    @InjectMocks
    private PaymentFeeManagerServiceImpl service;

    private PaymentFeeDTO paymentFeeDTO;
    private PaymentFee paymentFee;
    private final Long paymentOrderId = 1L;
    private final Long paymentFeeId = 2L;

    @BeforeEach
    void setUp() {
        // Setup test data
        paymentFeeDTO = PaymentFeeDTO.builder()
                .paymentFeeId(paymentFeeId)
                .paymentOrderId(paymentOrderId)
                .feeType("TRANSACTION")
                .feeAmount(new BigDecimal("10.00"))
                .feeCurrencyCode("EUR")
                .build();

        paymentFee = new PaymentFee();
        paymentFee.setPaymentFeeId(paymentFeeId);
        paymentFee.setPaymentOrderId(paymentOrderId);
        paymentFee.setFeeType("TRANSACTION");
        paymentFee.setFeeAmount(new BigDecimal("10.00"));
        paymentFee.setFeeCurrencyCode("EUR");
    }

    @Test
    void createPaymentFee_Success() {
        // Arrange
        when(mapper.toEntity(any(PaymentFeeDTO.class))).thenReturn(paymentFee);
        when(repository.save(any(PaymentFee.class))).thenReturn(Mono.just(paymentFee));
        when(mapper.toDTO(any(PaymentFee.class))).thenReturn(paymentFeeDTO);

        // Act & Assert
        StepVerifier.create(service.createPaymentFee(paymentOrderId, paymentFeeDTO))
                .expectNext(paymentFeeDTO)
                .verifyComplete();

        verify(mapper).toEntity(paymentFeeDTO);
        verify(repository).save(paymentFee);
        verify(mapper).toDTO(paymentFee);
    }

    @Test
    void getPaymentFeeById_Success() {
        // Arrange
        when(repository.findById(paymentFeeId)).thenReturn(Mono.just(paymentFee));
        when(mapper.toDTO(paymentFee)).thenReturn(paymentFeeDTO);

        // Act & Assert
        StepVerifier.create(service.getPaymentFeeById(paymentOrderId, paymentFeeId))
                .expectNext(paymentFeeDTO)
                .verifyComplete();

        verify(repository).findById(paymentFeeId);
        verify(mapper).toDTO(paymentFee);
    }

    @Test
    void getPaymentFeeById_NotFound() {
        // Arrange
        when(repository.findById(paymentFeeId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.getPaymentFeeById(paymentOrderId, paymentFeeId))
                .verifyComplete(); // Should complete without emitting any value

        verify(repository).findById(paymentFeeId);
        verify(mapper, never()).toDTO(any(PaymentFee.class));
    }

    @Test
    void getPaymentFeeById_WrongPaymentOrderId() {
        // Arrange
        Long wrongPaymentOrderId = 999L;
        when(repository.findById(paymentFeeId)).thenReturn(Mono.just(paymentFee));

        // Act & Assert
        StepVerifier.create(service.getPaymentFeeById(wrongPaymentOrderId, paymentFeeId))
                .verifyComplete(); // Should complete without emitting any value

        verify(repository).findById(paymentFeeId);
        verify(mapper, never()).toDTO(any(PaymentFee.class));
    }

    @Test
    void updatePaymentFee_Success() {
        // Arrange
        when(repository.findById(paymentFeeId)).thenReturn(Mono.just(paymentFee));
        when(mapper.toEntity(any(PaymentFeeDTO.class))).thenReturn(paymentFee);
        when(repository.save(any(PaymentFee.class))).thenReturn(Mono.just(paymentFee));
        when(mapper.toDTO(any(PaymentFee.class))).thenReturn(paymentFeeDTO);

        // Act & Assert
        StepVerifier.create(service.updatePaymentFee(paymentOrderId, paymentFeeId, paymentFeeDTO))
                .expectNext(paymentFeeDTO)
                .verifyComplete();

        verify(repository).findById(paymentFeeId);
        verify(mapper).toEntity(paymentFeeDTO);
        verify(repository).save(paymentFee);
        verify(mapper).toDTO(paymentFee);
    }

    @Test
    void updatePaymentFee_NotFound() {
        // Arrange
        when(repository.findById(paymentFeeId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.updatePaymentFee(paymentOrderId, paymentFeeId, paymentFeeDTO))
                .verifyComplete(); // Should complete without emitting any value

        verify(repository).findById(paymentFeeId);
        verify(mapper, never()).toEntity(any(PaymentFeeDTO.class));
        verify(repository, never()).save(any(PaymentFee.class));
    }

    @Test
    void updatePaymentFee_WrongPaymentOrderId() {
        // Arrange
        Long wrongPaymentOrderId = 999L;
        when(repository.findById(paymentFeeId)).thenReturn(Mono.just(paymentFee));

        // Act & Assert
        StepVerifier.create(service.updatePaymentFee(wrongPaymentOrderId, paymentFeeId, paymentFeeDTO))
                .verifyComplete(); // Should complete without emitting any value

        verify(repository).findById(paymentFeeId);
        verify(mapper, never()).toEntity(any(PaymentFeeDTO.class));
        verify(repository, never()).save(any(PaymentFee.class));
    }

    @Test
    void deletePaymentFee_Success() {
        // Arrange
        when(repository.findById(paymentFeeId)).thenReturn(Mono.just(paymentFee));
        when(repository.delete(paymentFee)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deletePaymentFee(paymentOrderId, paymentFeeId))
                .verifyComplete();

        verify(repository).findById(paymentFeeId);
        verify(repository).delete(paymentFee);
    }

    @Test
    void deletePaymentFee_NotFound() {
        // Arrange
        when(repository.findById(paymentFeeId)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(service.deletePaymentFee(paymentOrderId, paymentFeeId))
                .verifyComplete(); // Should complete without emitting any value

        verify(repository).findById(paymentFeeId);
        verify(repository, never()).delete(any(PaymentFee.class));
    }

    @Test
    void deletePaymentFee_WrongPaymentOrderId() {
        // Arrange
        Long wrongPaymentOrderId = 999L;
        when(repository.findById(paymentFeeId)).thenReturn(Mono.just(paymentFee));

        // Act & Assert
        StepVerifier.create(service.deletePaymentFee(wrongPaymentOrderId, paymentFeeId))
                .verifyComplete(); // Should complete without emitting any value

        verify(repository).findById(paymentFeeId);
        verify(repository, never()).delete(any(PaymentFee.class));
    }

    // Note: This test is a placeholder for testing the getAllPaymentFees method.
    // Since FilterUtils and PaginationResponse are external dependencies,
    // a more comprehensive test would require mocking these classes.
    @Test
    void getAllPaymentFees_ShouldCallFilterUtils() {
        // This test verifies that the service method is implemented and doesn't throw exceptions
        FilterRequest<PaymentFeeDTO> filterRequest = new FilterRequest<>();

        // Just verify that the method doesn't throw an exception
        service.getAllPaymentFees(paymentOrderId, filterRequest);
    }
}
