package com.playtomic.tests.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletInfoDTO {

    private String UUID;

    private BigDecimal amount;

}
