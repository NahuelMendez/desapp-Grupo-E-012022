package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;

public class IntentionResponse {
    private final String crypto;
    private final Integer nominalAmount;
    private final Integer cryptoPrice;
    private final Integer operationAmount;
    private final SimpleUser user;

    public IntentionResponse(Intention intention) {
        this.crypto = intention.getActiveCrypto();
        this.nominalAmount = intention.getNominalAmount();
        this.cryptoPrice = intention.getCryptoPrice();
        this.operationAmount = intention.getOperationAmount();
        this.user = new SimpleUser(intention.getUser());
    }

    public String getCrypto() {
        return crypto;
    }

    public Integer getNominalAmount() {
        return nominalAmount;
    }

    public Integer getCryptoPrice() {
        return cryptoPrice;
    }

    public Integer getOperationAmount() {
        return operationAmount;
    }

    public SimpleUser getUser() {
        return user;
    }

    /*○ Hora
      ○ Criptoactivo
      ○ Cantidad nominal del Cripto Activo
      ○ Cotización del Cripto Activo
      ○ Monto de la operación en pesos ARG
      .*/
}
