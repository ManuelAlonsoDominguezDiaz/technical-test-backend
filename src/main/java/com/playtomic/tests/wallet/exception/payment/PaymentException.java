package com.playtomic.tests.wallet.exception.payment;

import com.playtomic.tests.wallet.exception.MicroserviceGenericException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * As I have said on IStripService, the idea is to create a Factory Method pattern with other payments types (Paypal, Redsys, etc),
 * each one with their own exception types that extends from the PaymentException.
 * We could add common code for payments exceptions on this class.
 */
@Getter
public abstract class PaymentException extends MicroserviceGenericException {

    public PaymentException(String code, String message, HttpStatus status)
    {
        super(code, message, status);
    }

}
