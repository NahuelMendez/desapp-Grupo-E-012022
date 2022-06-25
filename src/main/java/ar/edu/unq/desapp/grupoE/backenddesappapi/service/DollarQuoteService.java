package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.service.provider.DollarQuoteProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DollarQuoteService {

    private final DollarQuoteProvider dollarQuoteProvider = new DollarQuoteProvider();

    public Double getDollarQuote(){
        return this.dollarQuoteProvider.getDollarQuote();
    }

}
