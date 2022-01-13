package com.playtomic.tests.wallet.it;

import com.playtomic.tests.wallet.WalletApplication;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = WalletApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class WalletApplicationIT {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturnHttpStatusOK_WhenExistingWallet() {

		String existingCreditCardUuid = "5b1280a3-f2a8-436b-8e0e-99e46278e441";

		ResponseEntity<WalletInfoDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/is-wallet-service/wallet/" + existingCreditCardUuid,
						WalletInfoDTO.class);
		Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
	}

	@Test
	public void shouldReturnHttpStatusNotFound_WhenUnexistingWallet() {

		String unexistingCreditCardUuid = "11111111-f2a8-436b-8e0e-99e46278e441";

		ResponseEntity<WalletInfoDTO> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/is-wallet-service/wallet/" + unexistingCreditCardUuid,
						WalletInfoDTO.class);
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());
	}

	/**
	 * MORE TEST WITH OTHERS CASUISTICS AND ENDPOINTS WILL BE HERE
	 */

}
