package com.playtomic.tests.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUpByCreditCardDTO {

    @Min(1)
    @ApiModelProperty(value = "amountToTopUp", example = "20")
    private BigDecimal amountToTopUp;

    @NotEmpty
    @ApiModelProperty(value = "creditCardNumber", example = "4646 4646 4646 4646")
    private String creditCardNumber;

}
