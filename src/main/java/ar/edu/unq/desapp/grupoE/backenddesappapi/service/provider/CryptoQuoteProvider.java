package ar.edu.unq.desapp.grupoE.backenddesappapi.service.provider;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CryptoQuoteProvider {

    private final String BASE_URL = "https://api1.binance.com/api/v3/ticker/";
    private WebClient webClient;
    private List<String> cryptoSymbols = Arrays.asList(
            "ALICEUSDT",
            "MATICUSDT",
            "AXSUSDT",
            "AAVEUSDT",
            "ATOMUSDT",
            "NEOUSDT",
            "DOTUSDT",
            "ETHUSDT",
            "CAKEUSDT",
            "BTCUSDT",
            "BNBUSDT",
            "ADAUSDT",
            "TRXUSDT",
            "AUDIOUSDT"
    );

    public CryptoQuoteProvider(){
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public CryptoQuote getCryptoQuoteBySymbol(String symbol){
        CryptoQuoteResponse response = this.webClient
                .get()
                .uri("/price?symbol=" + symbol)
                .retrieve()
                .bodyToMono(CryptoQuoteResponse.class)
                .block(/*Duration.ofSeconds(10)*/);

        return new CryptoQuote(response.getSymbol(), Double.valueOf(response.getPrice()), LocalDateTime.now());
    }

    public List<CryptoQuote> getAllCryptoQuotes() {
        List<CryptoQuote> resultList = new ArrayList<CryptoQuote>();
        this.cryptoSymbols.forEach((symbol) -> {
            resultList.add(getCryptoQuoteBySymbol(symbol));
        });

        return resultList;
    }

}

