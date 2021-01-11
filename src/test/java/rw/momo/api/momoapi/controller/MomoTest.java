package rw.momo.api.momoapi.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import rw.momo.api.momoapi.model.BalanceResponse;
import rw.momo.api.momoapi.model.EStatus;
import rw.momo.api.momoapi.model.PayRequest;
import rw.momo.api.momoapi.model.PayResponse;
import rw.momo.api.momoapi.model.Payer;
import rw.momo.api.momoapi.model.TokenResponse;
import rw.momo.api.momoapi.sevice.IMomoService;

@WebMvcTest(Momo.class)
public class MomoTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    IMomoService service;
    @Test
    public void getTokenSuccess () throws Exception {
        TokenResponse response = new TokenResponse();
        response.setAccess_token("access_token");
        response.setExpires_in(200);
        response.setToken_type("string");

        when(service.getToken()).thenReturn(response);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/momo/request/token").accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json("{access_token: access_token, expires_in: 200, token_type: string}")).andReturn();
    }

    @Test
    public void getBalance_when_success() throws Exception {
        BalanceResponse response = new BalanceResponse();
        response.setAvailableBalance("300");
        response.setCurrency("RWF");

        when(service.getBalance()).thenReturn(response);

        RequestBuilder builder = MockMvcRequestBuilders.get("/momo/request/balance").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(builder).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json("{availableBalance: \"300\", currency: RWF}")).andReturn();
    }

    @Test
    public void payRequest_when_success() throws Exception {
        UUID uuid = UUID.randomUUID();
        PayResponse response = new PayResponse();
        response.setAmount("100");
        response.setCurrency("RWF");
        response.setExternalId("externalId");
        response.setId(uuid);
        response.setPayeeNote("payeeNote");
        response.setPayerMessage("payerMessage");
        response.setReferenceId("referenceId");
        response.setStatus(EStatus.failed);
        response.setPayer(new Payer("partyIdType", "partyId"));

        Gson gson = new Gson();

        PayRequest request = new PayRequest();
        request.setAmount("100");
        request.setCurrency("RWF");
        request.setExternalId("externalId");
        request.setPayeeNote("payeeNote");
        request.setPayer(new Payer("partyIdType", "partyId"));
        request.setPayerMessage("payerMessage");

        String requestJson = gson.toJson(request);

        when(service.requestToPay(request))
                .thenReturn(new ResponseEntity<PayResponse>(response, HttpStatus.valueOf(202)));
        RequestBuilder builder = MockMvcRequestBuilders.post("/momo/request/pay").accept(MediaType.APPLICATION_JSON).content(requestJson);
        mockMvc.perform(builder).andExpect(status().is(200)).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json("{amount: \"100\",currency: RWF, externalId: externalId, payer: {partyIdType: MSISDN, partyId: \"46733123453\"},payerMessage: payerMessage,payeeNote: payeeNote}")).andReturn();

    }
}
