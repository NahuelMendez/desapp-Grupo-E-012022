package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

public class SaleIntention extends Intention {

    public SaleIntention(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount, User user) {
        super(activeCrypto, nominalAmount, cryptoPrice, operationAmount, user);
    }

    @Override
    public String shippingAddress() {
        return getUser().getCvu();
    }
}
