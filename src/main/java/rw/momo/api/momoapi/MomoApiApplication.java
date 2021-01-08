package rw.momo.api.momoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MomoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomoApiApplication.class, args);
	}
	
	@Bean
	public RestTemplate gTemplate() {
		return new RestTemplate();
	}

}
