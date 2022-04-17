package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

public class Asset {
    private String activeCrypto;
    private int nominalAmount;

    public Asset(String activeCrypto, int nominalAmount) {

        this.activeCrypto = activeCrypto;
        this.nominalAmount = nominalAmount;
    }

    public String activeCrypto() {
        return activeCrypto;
    }

    public int nominalAmount() {
        return nominalAmount;
    }
}
