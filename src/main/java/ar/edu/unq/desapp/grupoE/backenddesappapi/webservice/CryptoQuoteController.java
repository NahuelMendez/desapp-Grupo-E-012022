package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.CryptoQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@Validated
public class CryptoQuoteController {

    @Autowired
    private CryptoQuoteService cryptoQuoteService;

    @GetMapping("/api/crypto/{symbol}")
    public ResponseEntity<CryptoQuote> getCryptoQuotes(@PathVariable("symbol") String symbol){
        return  ResponseEntity.ok().body(this.cryptoQuoteService.getCryptoQuote(symbol));
    }

}
