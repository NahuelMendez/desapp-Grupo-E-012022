package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.util.List;

public class SaleIntention extends Intention {

    public SaleIntention(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount, User user, List<Crypto> quotes) throws UserException {
        super(activeCrypto, nominalAmount, cryptoPrice, operationAmount, user, quotes);
    }

    @Override
    public String shippingAddress() {
        return getUser().getCvu();
    }
}
