package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;

public class IntentionResponse {
    private final String crypto;
    private final Integer nominalAmount;
    private final Double cryptoPrice;
    private final Integer operationAmount;
    private final SimpleUser user;
    private final String intentionType;

    public IntentionResponse(Intention intention) {
        this.intentionType = intention.intentionType();
        this.crypto = intention.getActiveCrypto();
        this.nominalAmount = intention.getNominalAmount();
        this.cryptoPrice = intention.getCryptoPrice();
        this.operationAmount = intention.getOperationAmount();
        this.user = new SimpleUser(intention.getUser());
    }

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

    public Integer getOperationAmount() {
        return operationAmount;
    }

    public SimpleUser getUser() {
        return user;
    }

}
