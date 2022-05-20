package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class CryptoQuoteService {

    private final CryptoQuoteProvider cryptoQuoteProvider = new CryptoQuoteProvider();

    @Transactional
    public CryptoQuote getCryptoQuote(String symbol){
        CryptoQuoteResponse cryptoQuoteResponse = this.cryptoQuoteProvider.getCryptoQuoteBySymbol(symbol);
        return new CryptoQuote(cryptoQuoteResponse.getSymbol(), Float.parseFloat(cryptoQuoteResponse.getPrice()), LocalDateTime.now());
    }

}
