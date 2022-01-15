package com.playtomic.tests.wallet.unit.service;


import com.playtomic.tests.wallet.dto.RefundDTO;
import com.playtomic.tests.wallet.exception.payment.stripe.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.stripe.StripeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class StripeServiceTest {

    @Mock
    RestTemplateBuilder restTemplateBuilder;

    @Mock
    RestTemplate restTemplate;

    URI testUri = URI.create("http://how-would-you-test-me.localhost");

    StripeServiceImpl stripeService;

    @BeforeEach
    public void setup() {
        Mockito.when(restTemplateBuilder.errorHandler(Mockito.any())).thenReturn(restTemplateBuilder);
        Mockito.when(restTemplateBuilder.build()).thenReturn(restTemplate);
        stripeService = new StripeServiceImpl(testUri, testUri.toString(), restTemplateBuilder);
    }

    @Test
    public void shouldThownStripeAmountTooSmallException_WhenAmountTooLow() {

        // Given
        String creditCard = "4242 4242 4242 4242";
        BigDecimal amount = new BigDecimal(5);

        // When
        Mockito.when(restTemplate.postForObject(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(StripeAmountTooSmallException.class);

        // Then
        Assertions.assertThrows(StripeAmountTooSmallException.class, () ->
                stripeService.charge(creditCard, amount)
        );
        Mockito.verify(restTemplate,
                Mockito.times(1)).postForObject(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void shouldReturnVoid_WhenChargeIsOK() {

        // Given
        String creditCard = "4242 4242 4242 4242";
        BigDecimal amount = new BigDecimal(5);

        // When
        Mockito.when(restTemplate.postForObject(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new Object());
        stripeService.charge(creditCard, amount);

        // Then
        Mockito.verify(restTemplate,
                Mockito.times(1)).postForObject(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void shouldReturnRefundDTO_WhenRefundIsOK() {

        // Given
        String paymentId = UUID.randomUUID().toString();
        String refundId = UUID.randomUUID().toString();
        RefundDTO expectedRefundDTO = new RefundDTO(refundId, paymentId, BigDecimal.TEN);

        // When
        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.anyString()))
                .thenReturn(new ResponseEntity<>(expectedRefundDTO, HttpStatus.OK));
        RefundDTO actualRefundDTO = stripeService.refund(paymentId);

        // Then
        Mockito.verify(restTemplate,
                Mockito.times(1)).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.anyString());
        Assertions.assertEquals(expectedRefundDTO.getId(), actualRefundDTO.getId());
    }

    /**
     * MORE TEST WITH OTHERS CASUISTICS WILL BE HERE
     */

}
