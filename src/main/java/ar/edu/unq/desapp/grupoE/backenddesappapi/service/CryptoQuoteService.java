package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CryptoQuoteService {

    private final CryptoQuoteProvider cryptoQuoteProvider = new CryptoQuoteProvider();

    public CryptoQuote getCryptoQuote(String symbol){
        return this.cryptoQuoteProvider.getCryptoQuoteBySymbol(symbol);
    }

    public List<CryptoQuote> getAllCryptoQuotes(){
        return this.cryptoQuoteProvider.getAllCryptoQuotes();
    }



}
