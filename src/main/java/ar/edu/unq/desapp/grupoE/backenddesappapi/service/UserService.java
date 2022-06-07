package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IntentionRepository intentionRepository;
    @Autowired
    private CryptoQuoteService cryptoQuoteService;
    @Autowired
    private DollarQuoteService dollarQuoteService;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void expressIntention(Integer id, String crypto, Integer nominalAmount, Double cryptoPrice, String operation) throws UserException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("No se encontro el usuario"));
        Double dollarExchange = dollarQuoteService.getDollarQuote();
        Intention intention = createIntention(crypto, nominalAmount, cryptoPrice, dollarExchange, operation, user, getCryptoQuote(crypto));
        user.expressIntention(intention);
        intentionRepository.save(intention);
        userRepository.save(user);
    }

    private Intention createIntention(String crypto, Integer nominalAmount, Double cryptoPrice, Double dollarExchange, String operation, User user, CryptoQuote quote) throws UserException {
        if (operation.equals("buy")) {
            return new PurchaseIntention(crypto, nominalAmount, cryptoPrice, dollarExchange, user, quote);
        }else if (operation.equals("sale")) {
            return new SaleIntention(crypto, nominalAmount, cryptoPrice, dollarExchange, user, quote);
        }else {
            throw new UserException("No se puede crear la intention de tipo " + crypto + ".La intencion debe ser de tipo buy o sale");
        }
    }

    private CryptoQuote getCryptoQuote(String symbol) {
        return this.cryptoQuoteService.getCryptoQuote(symbol);
    }
}
