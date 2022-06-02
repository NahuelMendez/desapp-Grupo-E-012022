package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class DollarQuoteService {

    private final DollarQuoteProvider dollarQuoteProvider = new DollarQuoteProvider();

    @Transactional
    public Double getDollarQuote(){
        return this.dollarQuoteProvider.getDollarQuote();
    }

}
