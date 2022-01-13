package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.dto.TopUpDTO;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;

public interface IWalletService {

    WalletInfoDTO getWalletInfo(String uuid);

    WalletInfoDTO topUpWallet(String uuid, TopUpDTO topUpDTO);

    void decreaseWallet(String paymentId);
}
