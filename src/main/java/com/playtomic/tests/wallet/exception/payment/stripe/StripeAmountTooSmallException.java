package com.playtomic.tests.wallet.exception.payment.stripe;

import org.springframework.http.HttpStatus;

public class StripeAmountTooSmallException extends StripeServiceException {

    public StripeAmountTooSmallException(String code, String message, HttpStatus status)
    {
        super(code, message, status);
    }

}
