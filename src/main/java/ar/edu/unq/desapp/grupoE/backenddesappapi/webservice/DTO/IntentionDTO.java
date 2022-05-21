package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.PurchaseIntention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;

public class IntentionDTO {

    private String crypto;
    private Integer nominalAmount;
    private Integer cryptoPrice;
    private Integer operationAmount;
    private String operation;

    public IntentionDTO(
            String crypto,
            Integer nominalAmount,
            Integer cryptoPrice,
            Integer operationAmount,
            String operation) {

        this.crypto = crypto;
        this.nominalAmount = nominalAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationAmount = operationAmount;
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

    public Integer getCryptoPrice() {
        return cryptoPrice;
    }

    public void setCryptoPrice(Integer cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
    }

    public Integer getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(Integer operationAmount) {
        this.operationAmount = operationAmount;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /*Criptoactivo
○ Cantidad nominal del Cripto Activo
○ Cotización del Cripto Activo
○ Monto de la operación en pesos ARG
○ Usuario (Nombre/Apellido)
○ Operación: [COMPRA|VENTA]*/
}
