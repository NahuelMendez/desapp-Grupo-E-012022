package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

public class PurchaseIntention extends Intention {

    public PurchaseIntention(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount, User user) {
        super(activeCrypto, nominalAmount, cryptoPrice, operationAmount, user);
    }

    @Override
    public String shippingAddress() {
        return getUser().getWalletAddress();
    }
}
