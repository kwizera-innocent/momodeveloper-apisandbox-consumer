package rw.momo.api.momoapi.service;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import rw.momo.api.momoapi.model.TokenResponse;
import rw.momo.api.momoapi.sevice.MomoServiceImpl;
@RestClientTest(MomoServiceImpl.class)
// @AutoConfigureWebClient()
public class MomoServiceTestClient {
    @Autowired
    private MomoServiceImpl service;
    @Autowired
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    RestTemplate restTemplate;
    // @Autowired
    // private Environment env;
    @BeforeEach
    void setUp () {
        mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
    }
    @Test
    public void getTokenTests_when_success() throws Exception {
        String expResp = "{\"access_token\": \"token\",\"token_type\": \"string\",\"expires_in\": 0}";
        this.mockRestServiceServer.expect(MockRestRequestMatchers.requestTo("https://sandbox.momodeveloper.mtn.com/collection/token/")).andRespond(MockRestResponseCreators.withSuccess(expResp, MediaType.APPLICATION_JSON));
        Gson gson = new Gson();
        TokenResponse res = gson.fromJson(expResp, TokenResponse.class);
        TokenResponse res1 = service.getToken();
        assertEquals(res1.getAccess_token(), res.getAccess_token());
        
    }
    // @Test
    // public void getBalance_when_success() {
        
    // }

}
