package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.util.List;

public class PurchaseIntention extends Intention {

    public PurchaseIntention(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount, User user, List<CryptoQuote> quotes) throws UserException {
        super(activeCrypto, nominalAmount, cryptoPrice, operationAmount, user, quotes);
    }

    public Boolean thePriceIsNotWithinTheAllowedLimit(List<CryptoQuote> quotes) {
        CryptoQuote cryptoQuote = super.cryptoWithName(quotes, super.getActiveCrypto());
        return cryptoQuote.getPrice() > super.getCryptoPrice();
    }

    @Override
    public String shippingAddress() {
        return getUser().getWalletAddress();
    }
}
