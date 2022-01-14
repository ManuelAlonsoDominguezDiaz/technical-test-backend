package com.playtomic.tests.wallet.unit.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.api.WalletControllerImpl;
import com.playtomic.tests.wallet.dto.TopUpByCreditCardDTO;
import com.playtomic.tests.wallet.dto.WalletInfoDTO;
import com.playtomic.tests.wallet.service.wallet.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @InjectMocks
    private WalletControllerImpl walletControllerImpl;

    @Mock
    private WalletServiceImpl walletService;

    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletControllerImpl).build();
    }

    @Test
    public void should_ReturnHttpStatusOK_WhenExistingWallet() throws Exception {

        // Given
        String uuid = UUID.randomUUID().toString();

        // When
        Mockito.when(walletService.getWalletInfo(Mockito.anyString()))
                .thenReturn(new WalletInfoDTO(uuid, BigDecimal.valueOf(50)));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/wallet/" + uuid))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void should_ReturnHttpStatusOK_WhenTopUp_OK() throws Exception {

        // Given
        String uuid = UUID.randomUUID().toString();
        TopUpByCreditCardDTO topUpByCreditCardDTO = new TopUpByCreditCardDTO(BigDecimal.valueOf(50), "4444 5555 6666 7777 8888");

        // When
        Mockito.when(walletService.topUpWalletByCreditCard(Mockito.anyString(), Mockito.any()))
                .thenReturn(new WalletInfoDTO(uuid, BigDecimal.valueOf(50)));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/wallet/" + UUID.randomUUID() + "/top-up")
                        .content(mapper.writeValueAsString(topUpByCreditCardDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void shouldReturnHttpStatusOK_WhenRefund_OK() throws Exception {

        // Given
        String paymentId = UUID.randomUUID().toString();

        // When
        Mockito.doNothing().when(walletService).decreaseWallet(paymentId);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/wallet/refund/" + paymentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    /**
     * MORE TEST WITH OTHERS CASUISTICS WILL BE HERE
     */

}
