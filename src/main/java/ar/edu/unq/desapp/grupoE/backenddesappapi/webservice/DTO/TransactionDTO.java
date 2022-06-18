package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Transaction;

public class TransactionDTO {

    private Integer id;
    private String symbol;
    private Integer nominalAmount;
    private Double cryptoPrice;
    private Double operationAmount;
    private SimpleUserDTO user;
    private String shippingAddress;

    public TransactionDTO(Transaction transaction){
        Intention intention = transaction.getIntention();
        this.symbol = intention.getActiveCrypto();
        this.nominalAmount = intention.getNominalAmount();
        this.cryptoPrice = intention.getCryptoPrice();
        this.operationAmount = intention.operationAmount();
        this.user = new SimpleUserDTO(intention.getUser());
        this.shippingAddress = intention.shippingAddress();
        this.id = transaction.getId();
    }

    public TransactionDTO(){};

    public Integer getId() { return id; }

    public String getSymbol() {
        return symbol;
    }

    public Integer getNominalAmount() {
        return nominalAmount;
    }

    public Double getCryptoPrice() {
        return cryptoPrice;
    }

    public Double getOperationAmount() {
        return operationAmount;
    }

    public SimpleUserDTO getUser() {
        return user;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

}
