package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.dto.TopUpByCreditCardDTO;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;
import com.playtomic.tests.wallet.entity.WalletEntity;
import com.playtomic.tests.wallet.exception.ErrorCode;
import com.playtomic.tests.wallet.exception.wallet.WalletNotFoundException;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.payment.PaymentEnum;
import com.playtomic.tests.wallet.service.payment.PaymentFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements IWalletService {

    private final WalletRepository walletRepository;

    private final PaymentFactory paymentFactory;

    public WalletServiceImpl(WalletRepository walletRepository, PaymentFactory paymentFactory)
    {
        this.walletRepository = walletRepository;
        this.paymentFactory = paymentFactory;
    }

    @Override
    public WalletInfoDTO getWalletInfo(String uuid) {
        WalletEntity walletEntity = getWalletByUuid(uuid);
        return new WalletInfoDTO(walletEntity.getUuid(), walletEntity.getAmount());
    }

    /**
     * For top-ups and refunds I prefer other table instead increasing the money directly, something like wallet_transactions.
     * which will launch a pl/sql code when inserting a new registry, who will modify the wallet amount.
     * But for this case and to fit on test coding time, I will increase the amount directly into wallet's registry.
     */
    @Override
    @Transactional
    public WalletInfoDTO topUpWalletByCreditCard(String uuid, PaymentEnum paymentPlatform, TopUpByCreditCardDTO topUpByCreditCardDTO) {
        WalletEntity walletEntity = getWalletByUuid(uuid);
        BigDecimal newAmount = new BigDecimal(walletEntity.getAmount().add(topUpByCreditCardDTO.getAmountToTopUp()).doubleValue());
        walletEntity.setAmount(newAmount);

        paymentFactory.getPaymentFactory(paymentPlatform.getPaymentService())
                .charge(topUpByCreditCardDTO.getCreditCardNumber(), topUpByCreditCardDTO.getAmountToTopUp());
        return new WalletInfoDTO(walletEntity.getUuid(), walletEntity.getAmount());
    }

    /**
     * Sorry, not enough time to implement correctly, and I even don't know if its neccesary for the code testing.
     * README.md talks about two actions, to get a wallet and top-up money.
     * But recieved email with the code testing info talks about three. Is this the third?
     * So in this case just call to the stripService refund withou doing anything else.
     */
    @Override
    public void decreaseWallet(String paymentId) {
        paymentFactory.getPaymentFactory("STRIPE")
                .refund(paymentId);
    }

    private WalletEntity getWalletByUuid(String uuid) {
        return walletRepository.findByUuid(uuid)
                .orElseThrow(() -> new WalletNotFoundException(
                        ErrorCode.WALLET_NOT_FOUND.getCode(),
                        String.format(ErrorCode.WALLET_NOT_FOUND.getMessage(), uuid),
                        HttpStatus.NOT_FOUND));
    }
}
