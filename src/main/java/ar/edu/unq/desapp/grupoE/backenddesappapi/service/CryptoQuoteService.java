package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.CryptoRepository;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.provider.CryptoQuoteProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CryptoQuoteService {

    @Autowired
    CryptoRepository cryptoRepository;

    private final CryptoQuoteProvider cryptoQuoteProvider = new CryptoQuoteProvider();

    public CryptoQuote getCryptoQuote(String symbol){
        return this.cryptoQuoteProvider.getCryptoQuoteBySymbol(symbol);
    }

    public List<CryptoQuote> getAllCryptoQuotes(){
        return cryptoRepository.findAll();
    }

    @Async
    @Scheduled(fixedRate = 600000)
    public void saveAllCryptoQuotes() {
        cryptoRepository.saveAll(this.cryptoQuoteProvider.getAllCryptoQuotes());
    }
}
