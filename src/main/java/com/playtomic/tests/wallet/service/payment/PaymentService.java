package com.playtomic.tests.wallet.service.payment;

import com.playtomic.tests.wallet.dto.RefundDTO;
import com.playtomic.tests.wallet.exception.payment.PaymentException;
import lombok.NonNull;

import java.math.BigDecimal;

public interface PaymentService {

    void charge(@NonNull String creditCardNumber, @NonNull BigDecimal amount) throws PaymentException;

    RefundDTO refund(@NonNull String paymentId);

}
