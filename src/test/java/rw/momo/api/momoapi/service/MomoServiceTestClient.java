package rw.momo.api.momoapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import rw.momo.api.momoapi.model.BalanceResponse;
import rw.momo.api.momoapi.model.EStatus;
import rw.momo.api.momoapi.model.PayRequest;
import rw.momo.api.momoapi.model.PayResponse;
import rw.momo.api.momoapi.model.Payer;
import rw.momo.api.momoapi.model.TokenResponse;
import rw.momo.api.momoapi.sevice.MomoServiceImpl;
import rw.momo.api.momoapi.sevice.ResponseService;
@RestClientTest(MomoServiceImpl.class)
public class MomoServiceTestClient {
    @Autowired
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Environment env;
    @MockBean
    private ResponseService responseService;
    @Autowired
    private MomoServiceImpl service;
    @BeforeEach
    void setUp () {
        mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
    }
    @Test
    public void getTokenTests_when_success() throws Exception {
        String expResp = "{\"access_token\": \"token\",\"token_type\": \"string\",\"expires_in\": 0}";
        this.mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(env.getProperty("collection.token-url"))).andRespond(MockRestResponseCreators.withSuccess(expResp, MediaType.APPLICATION_JSON));
        Gson gson = new Gson();
        TokenResponse res = gson.fromJson(expResp, TokenResponse.class);
        TokenResponse res1 = service.getToken();
        assertEquals(res1.getAccess_token(), res.getAccess_token());
        
    }
    @Test
    public void getBalance_when_success() {
        String expToken = "{\"access_token\": \"token\",\"token_type\": \"string\",\"expires_in\": 0}";
        this.mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(env.getProperty("collection.token-url"))).andRespond(MockRestResponseCreators.withSuccess(expToken, MediaType.APPLICATION_JSON));

        String expResp = "{availableBalance: \"300\", currency: RWF}";
        this.mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(env.getProperty("collection.balance-url"))).andRespond(MockRestResponseCreators.withSuccess(expResp, MediaType.APPLICATION_JSON));
        Gson gson = new Gson();
        BalanceResponse balanceResponse = service.getBalance();
        BalanceResponse balanceResponse2 = gson.fromJson(expResp, BalanceResponse.class);
        assertTrue(balanceResponse.getAvailableBalance().equalsIgnoreCase(balanceResponse2.getAvailableBalance()));

    }
    @Test
    public void  payRequest_when_success() {
        String expToken = "{\"access_token\": \"token\",\"token_type\": \"string\",\"expires_in\": 0}";

        this.mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(env.getProperty("collection.token-url"))).andRespond(MockRestResponseCreators.withSuccess(expToken, MediaType.APPLICATION_JSON));
        
        String expResp = "{amount: \"100\",currency: RWF, externalId: externalId, payer: {partyIdType: MSISDN, partyId: \"46733123453\"},payerMessage: payerMessage,payeeNote: payeeNote}";

        this.mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(env.getProperty("collection.pay-request-url"))).andRespond(MockRestResponseCreators.withSuccess());

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
        
        PayRequest request = new PayRequest();
        request.setAmount("100");
        request.setCurrency("RWF");
        request.setExternalId("externalId");
        request.setPayeeNote("payeeNote");
        request.setPayer(new Payer("partyIdType", "partyId"));
        request.setPayerMessage("payerMessage");
        Gson gson = new Gson();

        when(responseService.createRequest(response)).thenReturn(response);

        PayResponse response1 = service.requestToPay(request).getBody();
        PayResponse response2 = gson.fromJson(expResp, PayResponse.class);

        assertTrue(response1.getReferenceId().equalsIgnoreCase(response2.getReferenceId()));

    }

}
