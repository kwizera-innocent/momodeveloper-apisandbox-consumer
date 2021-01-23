package rw.momo.api.momoapi.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class MomoConfig {
    @Value("${unexpired_token}")
    private String token;
    @Value("${token_expiration_time}")
    private LocalDateTime expires_in;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(LocalDateTime expires_in) {
        this.expires_in = expires_in;
    }


}
