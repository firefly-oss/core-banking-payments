package com.firefly.core.banking.payments.interfaces.dtos.beneficiary.v1;

import com.firefly.core.banking.payments.interfaces.dtos.BaseDTO;
import com.firefly.core.banking.payments.interfaces.enums.beneficiary.v1.BeneficiaryTypeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentBeneficiaryDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paymentBeneficiaryId;

    @FilterableId
    private Long payerAccountId;

    private String beneficiaryName;
    private BeneficiaryTypeEnum beneficiaryType;
    private String beneficiaryAccountNumber;
    private String beneficiaryIban;
    private String beneficiaryBic;
    private String beneficiaryAddress;
    private String beneficiaryCity;
    private String beneficiaryPostalCode;
    private String beneficiaryCountryCode;
    private String beneficiaryEmail;
    private String beneficiaryPhone;
    private String beneficiaryTaxId;
    private String beneficiaryBankName;
    private String beneficiaryBankAddress;
    private String beneficiaryBankCountryCode;
    private Boolean isFavorite;
    private String nickname;
}
