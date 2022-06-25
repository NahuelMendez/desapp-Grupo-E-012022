package ar.edu.unq.desapp.grupoE.backenddesappapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder build(){
        return WebClient.builder();
    }

}
