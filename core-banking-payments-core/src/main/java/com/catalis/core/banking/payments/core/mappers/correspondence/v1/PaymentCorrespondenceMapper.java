package com.catalis.core.banking.payments.core.mappers.correspondence.v1;

import com.catalis.core.banking.payments.interfaces.dtos.correspondence.v1.PaymentCorrespondenceDTO;
import com.catalis.core.banking.payments.models.entities.correspondence.v1.PaymentCorrespondence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentCorrespondenceMapper {
    PaymentCorrespondenceDTO toDTO(PaymentCorrespondence paymentCorrespondence);
    PaymentCorrespondence toEntity(PaymentCorrespondenceDTO paymentCorrespondenceDTO);
}
