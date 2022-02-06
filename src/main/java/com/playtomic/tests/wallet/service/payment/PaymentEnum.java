package com.playtomic.tests.wallet.service.payment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentEnum {

    STRIPE(PaymentEnum.STRIPE_SERVICE),
    PAYPAL(PaymentEnum.PAYPAL_SERVICE);

    public static final String STRIPE_SERVICE = "STRIPE_SERVICE";
    public static final String PAYPAL_SERVICE = "PAYPAL_SERVICE";

    private final String paymentService;

    public String getPaymentService() {
        return this.paymentService;
    }
}
