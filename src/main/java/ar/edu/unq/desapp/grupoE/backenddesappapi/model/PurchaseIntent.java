package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

public class PurchaseIntent extends OperationIntent {

    public PurchaseIntent(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount) {
        super(activeCrypto, nominalAmount, cryptoPrice, operationAmount);
    }

    @Override
    public String getShippingAddress(User user) {
        return user.getWalletAddress();
    }
}
