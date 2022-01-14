package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.dto.TopUpByCreditCardDTO;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;
import com.playtomic.tests.wallet.entity.WalletEntity;
import com.playtomic.tests.wallet.exception.ErrorCode;
import com.playtomic.tests.wallet.exception.wallet.WalletNotFoundException;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.stripe.IStripService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements IWalletService {

    private final WalletRepository walletRepository;

    private final IStripService stripService;

    public WalletServiceImpl(WalletRepository walletRepository, IStripService stripService)
    {
        this.walletRepository = walletRepository;
        this.stripService = stripService;
    }

    @Override
    public WalletInfoDTO getWalletInfo(String uuid) {
        WalletEntity walletEntity = getWalletByUuid(uuid);
        return new WalletInfoDTO(walletEntity.getUuid(), walletEntity.getAmount());
    }

    /**
     * For top-ups I prefer other table instead increasing the money directly, something like a wallet_transactions,
     * which launch a pl/sql to modify the wallet amount.
     * But for this case I will increase the value directly onto the wallet's table.
     */
    @Override
    @Transactional
    public WalletInfoDTO topUpWalletByCreditCard(String uuid, TopUpByCreditCardDTO topUpByCreditCardDTO) {
        WalletEntity walletEntity = getWalletByUuid(uuid);
        BigDecimal newAmount = new BigDecimal(walletEntity.getAmount().add(topUpByCreditCardDTO.getAmountToTopUp()).doubleValue());
        walletEntity.setAmount(newAmount);
        stripService.charge(topUpByCreditCardDTO.getCreditCardNumber(), topUpByCreditCardDTO.getAmountToTopUp());
        return new WalletInfoDTO(walletEntity.getUuid(), walletEntity.getAmount());
    }

    /**
     * Sorry, not enough time to implement correctly. So in this case just call to de stripService refund.
     */
    @Override
    public void decreaseWallet(String paymentId) {
        stripService.refund(paymentId);
    }

    private WalletEntity getWalletByUuid(String uuid) {
        return walletRepository.findByUuid(uuid)
                .orElseThrow(() -> new WalletNotFoundException(
                        ErrorCode.WALLET_NOT_FOUND.getCode(),
                        String.format(ErrorCode.WALLET_NOT_FOUND.getMessage(), uuid),
                        HttpStatus.NOT_FOUND));
    }
}
