package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;

public class CryptoQuoteProvider {

    private final String BASE_URL = "https://api1.binance.com/api/v3/ticker/";
    private WebClient webClient;

    public CryptoQuoteProvider(){
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public CryptoQuoteResponse getCryptoQuoteBySymbol(String symbol){
        return this.webClient
                .get()
                .uri("/price?symbol=" + symbol)
                .retrieve()
                .bodyToMono(CryptoQuoteResponse.class)
                .block(Duration.ofSeconds(3));
    }

}

