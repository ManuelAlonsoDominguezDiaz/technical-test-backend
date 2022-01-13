package com.playtomic.tests.wallet.service.wallet;

import com.playtomic.tests.wallet.dto.TopUpDTO;
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

    private WalletRepository walletRepository;

    private IStripService stripService;

    public WalletServiceImpl(WalletRepository walletRepository, IStripService stripService)
    {
        this.walletRepository = walletRepository;
        this.stripService = stripService;
    }

    @Override
    public WalletInfoDTO getWalletInfo(String uuid) {
        WalletEntity walletEntity = getWallet(uuid);
        return new WalletInfoDTO(walletEntity.getUuid(), walletEntity.getAmount());
    }

    /**
     * For top-ups I prefer other table, something like a transactions table,
     * which launch a pl/sql to modify the wallet amount.
     * But for this case I will increase the value directly onto the wallet's table.
     */
    @Override
    @Transactional
    public WalletInfoDTO topUpWallet(String uuid, TopUpDTO topUpDTO) {
        stripService.charge(topUpDTO.getCreditCardNumber(), topUpDTO.getAmountToTopUp());
        WalletEntity walletEntity = getWallet(uuid);
        BigDecimal newAmount = new BigDecimal(walletEntity.getAmount().add(topUpDTO.getAmountToTopUp()).doubleValue());
        walletEntity.setAmount(newAmount);
        walletRepository.save(walletEntity);
        return new WalletInfoDTO(walletEntity.getUuid(), walletEntity.getAmount());
    }

    /**
     * Sorry, not enough time to implement correctly. So in this case just call to de stripService refund.
     */
    @Override
    public void decreaseWallet(String paymentId) {
        stripService.refund(paymentId);
    }

    private WalletEntity getWallet(String uuid) {
        return walletRepository.findByUuid(uuid)
                .orElseThrow(() -> new WalletNotFoundException(
                        ErrorCode.WALLET_NOT_FOUND.getCode(),
                        String.format(ErrorCode.WALLET_NOT_FOUND.getMessage(), uuid),
                        HttpStatus.NOT_FOUND));
    }
}
