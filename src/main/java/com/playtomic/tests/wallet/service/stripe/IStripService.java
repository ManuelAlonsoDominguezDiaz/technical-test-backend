package com.playtomic.tests.wallet.service.stripe;

import com.playtomic.tests.wallet.dto.RefundDTO;
import com.playtomic.tests.wallet.exception.payment.stripe.StripeServiceException;
import lombok.NonNull;

import java.math.BigDecimal;

/**
 * As I've said on PaymentException, could be a good idea could be to create a Factory Pattern with different payments types (Paypal, Redsys, etc),
 * but in this case I will keep it simple and use only the asked one for the code test.
 * Could be also a good idea to separate wallet and payments in two microservices, decoupled by a message broker.
 */
public interface IStripService {

    void charge(@NonNull String creditCardNumber, @NonNull BigDecimal amount) throws StripeServiceException;

    RefundDTO refund(@NonNull String paymentId);

}
