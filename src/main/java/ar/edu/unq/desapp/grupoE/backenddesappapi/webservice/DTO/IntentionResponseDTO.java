package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;

import java.time.LocalDateTime;

public class IntentionResponseDTO {

    private Integer id;
    private LocalDateTime dateTime;
    private String crypto;
    private Integer nominalAmount;
    private Double cryptoPrice;
    private Double operationAmount;
    private SimpleUserDTO user;
    private String intentionType;

    public IntentionResponseDTO(){};

    public IntentionResponseDTO(Intention intention) {
        this.id = intention.getId();
        this.dateTime = intention.getDateTime();
        this.intentionType = intention.intentionType();
        this.crypto = intention.getActiveCrypto();
        this.nominalAmount = intention.getNominalAmount();
        this.cryptoPrice = intention.getCryptoPrice();
        this.operationAmount = intention.operationAmount();
        this.user = new SimpleUserDTO(intention.getUser());
    }

    public Integer getId() {
        return id;
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

    public SimpleUserDTO getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public void setNominalAmount(Integer nominalAmount) {
        this.nominalAmount = nominalAmount;
    }

    public void setCryptoPrice(Double cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
    }

    public void setOperationAmount(Double operationAmount) {
        this.operationAmount = operationAmount;
    }

    public void setUser(SimpleUserDTO user) {
        this.user = user;
    }

    public void setIntentionType(String intentionType) {
        this.intentionType = intentionType;
    }

}
