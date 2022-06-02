package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.service.DollarQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
@Validated
public class DollarQuoteController {

    @Autowired
    private DollarQuoteService dollarQuoteService;

    @GetMapping("/api/dollar/")
    public ResponseEntity<Double> getDollarQuote(){
        return  ResponseEntity.ok().body(this.dollarQuoteService.getDollarQuote());
    }

}
