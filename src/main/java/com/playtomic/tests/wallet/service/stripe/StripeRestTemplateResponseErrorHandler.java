package com.playtomic.tests.wallet.service.stripe;

import com.playtomic.tests.wallet.exception.ErrorCode;
import com.playtomic.tests.wallet.exception.payment.stripe.StripeAmountTooSmallException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class StripeRestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {

        if (statusCode == HttpStatus.UNPROCESSABLE_ENTITY) {
            throw new StripeAmountTooSmallException(ErrorCode.AMOUNT_TOO_SMALL.getCode(),
                    ErrorCode.AMOUNT_TOO_SMALL.getMessage(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        super.handleError(response, statusCode);
    }
}