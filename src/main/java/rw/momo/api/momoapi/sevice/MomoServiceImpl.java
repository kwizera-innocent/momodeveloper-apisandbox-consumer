package rw.momo.api.momoapi.sevice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rw.momo.api.momoapi.model.BalanceResponse;
import rw.momo.api.momoapi.model.TokenResponse;
import rw.momo.api.momoapi.model.PayRequest;
import rw.momo.api.momoapi.model.payer;

@Service
public class MomoServiceImpl implements IMomoService {
    Environment env;
    RestTemplate template;

    Logger LOGGER = LoggerFactory.getLogger(MomoServiceImpl.class);

    @Autowired
    public MomoServiceImpl(Environment env, RestTemplate template) {
        this.env = env;
        this.template = template;
    }

    @Override
    public TokenResponse getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", env.getProperty("Authorization"));
        headers.add("Ocp-Apim-Subscription-Key", env.getProperty("Ocp-Apim-Subscription-Key"));

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<TokenResponse> responseEntity = template.exchange(env.getProperty("collection.token-url"),
                HttpMethod.POST, entity, new ParameterizedTypeReference<TokenResponse>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public BalanceResponse getBalance() {
        String token = getToken().getAccess_token();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + token);
        headers.set("X-Target-Environment", env.getProperty("X-Target-Environment"));
        headers.add("Ocp-Apim-Subscription-Key", env.getProperty("Ocp-Apim-Subscription-Key"));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        LOGGER.info("headers: {}", headers);
        LOGGER.info("url: {}", env.getProperty("collection.balance-url"));
        try {
            ResponseEntity<?> balance = template.exchange(env.getProperty("collection.balance-url"),
                    HttpMethod.GET, entity, new ParameterizedTypeReference<String>() {
                    });
            // ObjectMapper mapper = new ObjectMapper();
            Gson gson = new Gson();
            BalanceResponse response = gson.fromJson((String) balance.getBody(), BalanceResponse.class);
            // mapper.readValue(balance.getBody(), BalanceResponse.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error: {}", e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> requestToPay(PayRequest request) {
        String token = getToken().getAccess_token();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + token);
        // headers.add("X-Callback-Url", "localhost:9004/momo/callback/pay-request");
        String refId = UUID.randomUUID().toString();
        headers.add("X-Reference-Id", refId);
        headers.set("X-Target-Environment", env.getProperty("X-Target-Environment"));
        headers.add("Ocp-Apim-Subscription-Key", env.getProperty("Ocp-Apim-Subscription-Key"));

        HttpEntity<PayRequest> entity = new HttpEntity<PayRequest>(request, headers);

        LOGGER.info("request: {}", request.toString());
        LOGGER.info("headers: {}", headers);
        LOGGER.info("url: {}", env.getProperty("collection.balance-url"));

        ResponseEntity<?> responseEntity = template.exchange(env.getProperty("collection.pay-request-url"),
                HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
                });
        Map<String, String> body = new HashMap<>();
        body.put("Reference-Id", refId);
        return new ResponseEntity<Map<String,String>>(body ,responseEntity.getStatusCode());
    }

    @Override
    public ResponseEntity<?> requestStatus(String referenceId) {
        String token = getToken().getAccess_token();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + token);
        headers.set("X-Target-Environment", env.getProperty("X-Target-Environment"));
        headers.add("Ocp-Apim-Subscription-Key", env.getProperty("Ocp-Apim-Subscription-Key"));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = env.getProperty("collection.request-status-url") + referenceId;
        ResponseEntity<?> responseEntity = template.exchange(url,
        HttpMethod.GET, entity, new ParameterizedTypeReference<String>() {
        });

        return responseEntity;
    }
    
    
}
