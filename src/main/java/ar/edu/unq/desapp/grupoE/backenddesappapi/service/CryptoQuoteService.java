package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CryptoQuoteService {

    private final CryptoQuoteProvider cryptoQuoteProvider = new CryptoQuoteProvider();

    @Transactional
    public CryptoQuote getCryptoQuote(String symbol){
        return this.cryptoQuoteProvider.getCryptoQuoteBySymbol(symbol);
    }

    @Transactional
    public List<CryptoQuote> getAllCryptoQuotes(){
        return this.cryptoQuoteProvider.getAllCryptoQuotes();
    }



}
