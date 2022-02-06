package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.TopUpByCreditCardDTO;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;
import com.playtomic.tests.wallet.service.payment.PaymentEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/wallet")
@ApiModel(value = "Wallet controller", description = "Controller to manage wallet operations")
public interface IWalletController {

    @GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<WalletInfoDTO> getWalletInfo(@PathVariable @ApiParam(example = "5b1280a3-f2a8-436b-8e0e-99e46278e441") String uuid);

    @PutMapping(path = "/{uuid}/top-up/{paymentPlatform}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<WalletInfoDTO> topUpWalletByCreditCard(
            @PathVariable @ApiParam(example = "5b1280a3-f2a8-436b-8e0e-99e46278e441") String uuid, @PathVariable PaymentEnum paymentPlatform,
            @Valid @RequestBody TopUpByCreditCardDTO topUpByCreditCardDTO);

    @PutMapping(path = "/refund/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> refundWallet(@PathVariable String paymentId);
}
