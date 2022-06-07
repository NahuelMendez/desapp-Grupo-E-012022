package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;

import java.time.LocalDateTime;

public class IntentionResponse {

    private final LocalDateTime dateTime;
    private final String crypto;
    private final Integer nominalAmount;
    private final Double cryptoPrice;
    private final Double operationAmount;
    private final SimpleUser user;
    private final String intentionType;

    public IntentionResponse(Intention intention) {
        this.dateTime = intention.getDateTime();
        this.intentionType = intention.intentionType();
        this.crypto = intention.getActiveCrypto();
        this.nominalAmount = intention.getNominalAmount();
        this.cryptoPrice = intention.getCryptoPrice();
        this.operationAmount = intention.operationAmount();
        this.user = new SimpleUser(intention.getUser());
    }

    public LocalDateTime getDateTime() { return dateTime; }

    public String getIntentionType() {
        return intentionType;
    }

    public String getCrypto() {
        return crypto;
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

}
