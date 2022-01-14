package com.playtomic.tests.wallet.unit.service;

import com.playtomic.tests.wallet.dto.TopUpByCreditCardDTO;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;
import com.playtomic.tests.wallet.entity.WalletEntity;
import com.playtomic.tests.wallet.exception.ErrorCode;
import com.playtomic.tests.wallet.exception.wallet.WalletNotFoundException;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.stripe.StripeServiceImpl;
import com.playtomic.tests.wallet.service.wallet.WalletServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    WalletRepository walletRepository;

    @Mock
    StripeServiceImpl stripeService;

    @InjectMocks
    WalletServiceImpl walletService;

    @Test
    public void should_ReturnWalletInfo_WhenExistingWallet() {

        // Given
        String uuid = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.valueOf(50);
        WalletInfoDTO walletInfoDTO = new WalletInfoDTO(uuid, amount);
        WalletEntity walletEntity = new WalletEntity(1, uuid, amount);

        // When
        Mockito.when(walletRepository.findByUuid(walletInfoDTO.getUUID())).thenReturn(Optional.of(walletEntity));


        // Then
        Assertions.assertEquals(walletInfoDTO.getUUID(), walletService.getWalletInfo(walletInfoDTO.getUUID()).getUUID());
    }

    @Test
    public void should_ThrownWalletNotFoundException_WhenNonExistingUuid() throws WalletNotFoundException{

        // Given
        String uuid = UUID.randomUUID().toString();

        // When
        Mockito.when(walletRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(WalletNotFoundException.class, () -> walletService.getWalletInfo(uuid),
                ErrorCode.WALLET_NOT_FOUND.getMessage());
    }

    @Test
    public void should_TopUpWalletToTwenty_WhenValueIsTen() {

        // Given
        String uuid = UUID.randomUUID().toString();
        TopUpByCreditCardDTO topUpByCreditCardDTO = new TopUpByCreditCardDTO(BigDecimal.TEN, "1111 2222 3333 4444");
        WalletEntity walletEntity = new WalletEntity(1L, uuid, BigDecimal.TEN);

        // When
        Mockito.when(walletRepository.findByUuid(uuid)).thenReturn(Optional.of(walletEntity));
        Mockito.doNothing().when(stripeService).charge(topUpByCreditCardDTO.getCreditCardNumber(), topUpByCreditCardDTO.getAmountToTopUp());
        WalletInfoDTO walletEntityReturn = walletService.topUpWalletByCreditCard(uuid, topUpByCreditCardDTO);

        // Then
        Assertions.assertEquals(new BigDecimal(20), walletEntityReturn.getAmount());
    }

    /**
     * MORE TEST WITH OTHERS CASUISTICS WILL BE HERE
     */

}