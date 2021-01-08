package rw.momo.api.momoapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import rw.momo.api.momoapi.model.TokenResponse;
import rw.momo.api.momoapi.sevice.MomoServiceImpl;

public class MomoServiceTest {
    private Environment env;
    RestTemplate template;
    // @Autowired
    MomoServiceImpl service = new MomoServiceImpl(env, template);
    @Test
    public void getTokenTests_when_success() throws NullPointerException {
        try {
            TokenResponse res = service.getToken();
            assertEquals(res,"hehe");
        } catch (Exception e) {
            assertEquals("resp","hehe");
        }
        
        
    }
}
