package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.TopUpByCreditCardDTO;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;
import com.playtomic.tests.wallet.service.payment.PaymentEnum;
import com.playtomic.tests.wallet.service.wallet.IWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletControllerImpl implements IWalletController {

    private final IWalletService walletService;

    public WalletControllerImpl(IWalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public ResponseEntity<WalletInfoDTO> getWalletInfo(@PathVariable String uuid) {
        return new ResponseEntity<>(walletService.getWalletInfo(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WalletInfoDTO> topUpWalletByCreditCard(String uuid, PaymentEnum paymentPlatform, TopUpByCreditCardDTO topUpByCreditCardDTO) {
        return new ResponseEntity<>(walletService.topUpWalletByCreditCard(uuid, paymentPlatform, topUpByCreditCardDTO), HttpStatus.OK);
    }

    /**
     * Not finished, sorry.
     */
    @Override
    public ResponseEntity<Void> refundWallet(String paymentId) {
        walletService.decreaseWallet(paymentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
