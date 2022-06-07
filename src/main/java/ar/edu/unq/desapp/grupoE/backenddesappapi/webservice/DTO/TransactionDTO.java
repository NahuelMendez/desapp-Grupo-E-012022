package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Transaction;

public class TransactionDTO {

    private String symbol;
    private Integer nominalAmount;
    private Double cryptoPrice;
    private Double operationAmount;
    private SimpleUser user;
    private String shippingAddress;

    public TransactionDTO(Transaction transaction){
        Intention intention = transaction.getIntention();
        this.symbol = intention.getActiveCrypto();
        this.nominalAmount = intention.getNominalAmount();
        this.cryptoPrice = intention.getCryptoPrice();
        this.operationAmount = intention.operationAmount();
        this.user = new SimpleUser(intention.getUser());
        this.shippingAddress = intention.shippingAddress();
    }

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

    public SimpleUser getUser() {
        return user;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

}
