package com.playtomic.tests.wallet.exception.payment.stripe;

import com.playtomic.tests.wallet.exception.payment.PaymentException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StripeServiceException extends PaymentException {
    public StripeServiceException(String code, String message, HttpStatus status)
    {
        super(code, message, status);
    }
}
