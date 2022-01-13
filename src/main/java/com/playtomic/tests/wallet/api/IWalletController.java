package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.TopUpDTO;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.Example;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/wallet")
@ApiModel(value = "Wallet controller", description = "Controller to manage wallet operations")
public interface IWalletController {

    @GetMapping(path = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<WalletInfoDTO> getWalletInfo(@PathVariable String uuid);

    @PostMapping(path = "/{uuid}/top-up", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<WalletInfoDTO> topUpWallet(@PathVariable String uuid, @Valid @RequestBody TopUpDTO topUpDTO);

    @PostMapping(path = "/refund/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> refundWallet(@PathVariable String paymentId);
}
