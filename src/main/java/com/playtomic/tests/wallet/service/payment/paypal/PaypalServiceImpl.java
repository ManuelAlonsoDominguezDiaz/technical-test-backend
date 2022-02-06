package com.playtomic.tests.wallet.service.payment.paypal;

import com.playtomic.tests.wallet.dto.RefundDTO;
import com.playtomic.tests.wallet.exception.payment.PaymentException;
import com.playtomic.tests.wallet.service.payment.PaymentEnum;
import com.playtomic.tests.wallet.service.payment.PaymentService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service(PaymentEnum.PAYPAL_SERVICE)
public class PaypalServiceImpl implements PaymentService {
    @Override
    public void charge(@NonNull String creditCardNumber, @NonNull BigDecimal amount) throws PaymentException {
        System.out.println("TESTING PAYMENT FACTORY: PAYPAL BEAN");
    }

    @Override
    public RefundDTO refund(@NonNull String paymentId) {
        return null;
    }
}
