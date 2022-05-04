package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.util.List;

public class PurchaseIntention extends Intention {

    public PurchaseIntention(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount, User user, List<Crypto> quotes) throws UserException {
        super(activeCrypto, nominalAmount, cryptoPrice, operationAmount, user, quotes);
    }

    public Boolean thePriceIsNotWithinTheAllowedLimit(List<Crypto> quotes) {
        Crypto crypto = super.cryptoWithName(quotes, super.getActiveCrypto());
        return crypto.getPrice() > super.getCryptoPrice();
    }

    @Override
    public String shippingAddress() {
        return getUser().getWalletAddress();
    }
}
