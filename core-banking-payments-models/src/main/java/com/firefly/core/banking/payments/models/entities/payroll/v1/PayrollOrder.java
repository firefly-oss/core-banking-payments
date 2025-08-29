package com.firefly.core.banking.payments.models.entities.payroll.v1;

import com.firefly.core.banking.payments.interfaces.enums.payroll.v1.PayrollStatusEnum;
import com.firefly.core.banking.payments.models.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payroll_order")
public class PayrollOrder extends BaseEntity {

    @Id
    @Column("payroll_order_id")
    private Long payrollOrderId;

    @Column("payment_order_id")
    private Long paymentOrderId;

    @Column("payroll_reference")
    private String payrollReference;

    @Column("payroll_date")
    private LocalDateTime payrollDate;

    @Column("total_amount")
    private BigDecimal totalAmount;

    @Column("payroll_status")
    private PayrollStatusEnum payrollStatus;
}