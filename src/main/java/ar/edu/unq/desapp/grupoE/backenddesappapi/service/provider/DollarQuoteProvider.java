package ar.edu.unq.desapp.grupoE.backenddesappapi.service.provider;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.util.List;

public class DollarQuoteProvider {

        private static final String BASE_URL = "https://www.dolarsi.com/api/api.php?type=valoresprincipales";
        private final WebClient webClient;


        public DollarQuoteProvider(){
            this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
        }

        public Double getDollarQuote(){
            List<DollarQuoteResponse> response = this.webClient
                    .get()
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<DollarQuoteResponse>>() {})
                    .block(Duration.ofSeconds(10));

            return Double.valueOf(response.get(0).getCasa().getCompra().replace(",", "."));
        }

}
