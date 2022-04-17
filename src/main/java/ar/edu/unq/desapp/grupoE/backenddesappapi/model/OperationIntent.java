package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

public abstract class OperationIntent {

    private String activeCrypto;
    private int nominalAmount;
    private int cryptoPrice;
    private int operationAmount;

    public OperationIntent(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount) {
        this.activeCrypto = activeCrypto;
        this.nominalAmount = nominalAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationAmount = operationAmount;
    }

    public String getActiveCrypto(){
        return activeCrypto;
    }

    public int getNominalAmount() {
        return nominalAmount;
    }

    public int getCryptoPrice() {
        return cryptoPrice;
    }

    public int getOperationAmount() {
        return operationAmount;
    }

    public abstract String getShippingAddress(User user);
}
