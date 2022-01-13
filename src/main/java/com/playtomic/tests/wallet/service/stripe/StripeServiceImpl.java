package com.playtomic.tests.wallet.service.stripe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.playtomic.tests.wallet.dto.RefundDTO;
import com.playtomic.tests.wallet.exception.payment.stripe.StripeServiceException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;


/**
 * Handles the communication with Stripe.
 *
 * A real implementation would call to String using their API/SDK.
 * This dummy implementation throws an error when trying to charge less than 10€.
 */
@Service
public class StripeServiceImpl implements IStripService {

    @NonNull
    private URI chargesUri;

    @NonNull
    private String refundsUri;

    @NonNull
    private RestTemplate restTemplate;

    public StripeServiceImpl(@Value("${stripe.simulator.charges-uri}") @NonNull URI chargesUri,
                             @Value("${stripe.simulator.refunds-uri}") @NonNull String refundsUri,
                             @NonNull RestTemplateBuilder restTemplateBuilder) {
        this.chargesUri = chargesUri;
        this.refundsUri = refundsUri;
        this.restTemplate =
                restTemplateBuilder
                .errorHandler(new StripeRestTemplateResponseErrorHandler())
                .build();
    }

    /**
     * Charges money in the credit card.
     *
     * Ignore the fact that no CVC or expiration date are provided.
     *
     * @param creditCardNumber The number of the credit card
     * @param amount The amount that will be charged.
     *
     * @throws StripeServiceException
     */
    public void charge(@NonNull String creditCardNumber, @NonNull BigDecimal amount) throws StripeServiceException {
        ChargeRequest body = new ChargeRequest(creditCardNumber, amount);
        restTemplate.postForObject(chargesUri, body, Object.class);
    }

    /**
     * Refunds the specified payment.
     */
    public RefundDTO refund(@NonNull String paymentId) throws StripeServiceException {
        return restTemplate.postForEntity(refundsUri.toString(), null, RefundDTO.class, paymentId).getBody();
    }

    @AllArgsConstructor
    private static class ChargeRequest {

        @NonNull
        @JsonProperty("credit_card")
        String creditCardNumber;

        @NonNull
        @JsonProperty("amount")
        BigDecimal amount;
    }
}
