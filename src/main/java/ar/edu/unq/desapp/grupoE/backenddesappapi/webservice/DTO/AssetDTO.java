package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoReport;

public class AssetDTO {

    private final String crypto;
    private final Integer nominalAmount;
    private final Double priceInUsd;
    private final Double priceInPesos;

    public AssetDTO(CryptoReport asset) {
        this.crypto = asset.getCrypto();
        this.nominalAmount = asset.getNominalAmount();
        this.priceInUsd = asset.getPriceInUsd();
        this.priceInPesos = asset.getPriceInPesos();
    }

    public String getCrypto() {
        return crypto;
    }

    public Integer getNominalAmount() {
        return nominalAmount;
    }

    public Double getPriceInUsd() {
        return priceInUsd;
    }

    public Double getPriceInPesos() {
        return priceInPesos;
    }
}
