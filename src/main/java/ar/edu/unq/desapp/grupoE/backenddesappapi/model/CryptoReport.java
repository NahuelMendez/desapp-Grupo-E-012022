package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

public class CryptoReport {

    private CryptoMovement cryptoMovement;
    private CryptoQuote cryptoQuote;
    private Double dollarQuote;

    public CryptoReport(CryptoMovement cryptoMovement, CryptoQuote cryptoQuote, Double dollarQuote) {

        this.cryptoMovement = cryptoMovement;
        this.cryptoQuote = cryptoQuote;
        this.dollarQuote = dollarQuote;
    }

    public String getCrypto() {
        return cryptoQuote.getName();
    }

    public Integer getNominalAmount() {
        return cryptoMovement.getNominalAmount();
    }

    public Double getPriceInUsd() {
        return cryptoQuote.getPrice();
    }

    public Double getPriceInPesos() {
        return getPriceInUsd() * dollarQuote;
    }
}
