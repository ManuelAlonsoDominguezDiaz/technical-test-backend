package com.playtomic.tests.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUpDTO {

    @Min(1)
    private BigDecimal amountToTopUp;

    @NotEmpty
    private String creditCardNumber;

}
