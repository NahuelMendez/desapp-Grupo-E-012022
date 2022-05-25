package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class PurchaseIntention extends Intention {

    public PurchaseIntention(){super();}

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

    @Override
    public String intentionType() {
        return "buy";
    }
}
