package com.playtomic.tests.wallet.service.stripe;

import com.playtomic.tests.wallet.dto.RefundDTO;
import com.playtomic.tests.wallet.exception.payment.stripe.StripeServiceException;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * As said on PaymentException, maybe a good idea could be to create a Factory Pattern with different payments options,
 * but in this case I will keep it simple and use only the asked for the code test.
 * Could be also a good idea to separate wallet and payments in two services, decoupled by a message broker.
 */
public interface IStripService {

    void charge(@NonNull String creditCardNumber, @NonNull BigDecimal amount) throws StripeServiceException;

    RefundDTO refund(@NonNull String paymentId);

}
