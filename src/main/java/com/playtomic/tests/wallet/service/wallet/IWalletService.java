package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.dto.TopUpByCreditCardDTO;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;
import com.playtomic.tests.wallet.service.payment.PaymentEnum;

public interface IWalletService {

    WalletInfoDTO getWalletInfo(String uuid);

    WalletInfoDTO topUpWalletByCreditCard(String uuid, PaymentEnum paymentPlatform, TopUpByCreditCardDTO topUpByCreditCardDTO);

    void decreaseWallet(String paymentId);
}
