package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.PurchaseIntention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;

public class IntentionDTO {

    private String crypto;
    private Integer nominalAmount;
    private Double cryptoPrice;
    private String operation;

    public IntentionDTO(
            String crypto,
            Integer nominalAmount,
            Double cryptoPrice,
            String operation) {

        this.crypto = crypto;
        this.nominalAmount = nominalAmount;
        this.cryptoPrice = cryptoPrice;
        this.operation = operation;
    }

    public String getCrypto() {
        return crypto;
    }

    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public Integer getNominalAmount() {
        return nominalAmount;
    }

    public void setNominalAmount(Integer nominalAmount) {
        this.nominalAmount = nominalAmount;
    }

    public Double getCryptoPrice() {
        return cryptoPrice;
    }

    public void setCryptoPrice(Double cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

}
