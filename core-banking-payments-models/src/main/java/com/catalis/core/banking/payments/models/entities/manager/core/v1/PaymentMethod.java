package com.catalis.core.banking.payments.models.entities.manager.core.v1;

import com.catalis.core.banking.payments.models.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("payment_method")
public class PaymentMethod extends BaseEntity {
    @Id
    @Column("payment_method_id")
    private Long paymentMethodId;

    @Column("method_name")
    private String methodName;

    @Column("description")
    private String description;

    @Column("active_flag")
    private Boolean activeFlag;
}
