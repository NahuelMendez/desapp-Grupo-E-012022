package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.CryptoRepository;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.provider.CryptoQuoteProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
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

    public List<CryptoQuote> getAllCryptoQuoteFor(String symbol){
        List<CryptoQuote> quotes = cryptoRepository.findAllCryptoQuoteFor(symbol);
        if (quotes.isEmpty()) {
            return Collections.singletonList(this.cryptoQuoteProvider.getCryptoQuoteBySymbol(symbol));
        }
        return quotes;
    }

    public List<CryptoQuote> getAllCryptoQuotes(){
        return cryptoRepository.findCryptoQuoteLastUpdate();
    }

    @Async
    @Scheduled(fixedDelay = 600000)
    public void saveAllCryptoQuotes() {
        cryptoRepository.saveAll(this.cryptoQuoteProvider.getAllCryptoQuotes());
    }

    @Async
    @Scheduled(cron = "0 0 */1 * * *")
    public void deleteCryptoQuotePast24hr() {
        cryptoRepository.deleteCryptoQuotePast24hr(LocalDateTime.now().minusHours(24));
    }
}
