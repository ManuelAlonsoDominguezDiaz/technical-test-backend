package com.playtomic.tests.wallet.exception.wallet;

import com.playtomic.tests.wallet.exception.MicroserviceGenericException;
import org.springframework.http.HttpStatus;

public class WalletNotFoundException extends MicroserviceGenericException {

    /**
     * ¿Dónde está Wallet? Sorry, I couldn't avoid that.
     */
    public WalletNotFoundException(String code, String message, HttpStatus status) {
        super(code, message, status);
    }

}
