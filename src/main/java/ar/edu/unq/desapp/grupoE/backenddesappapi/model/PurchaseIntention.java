package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.Entity;

@Entity
public class PurchaseIntention extends Intention {

    public PurchaseIntention(){super();}

    public PurchaseIntention(String activeCrypto, int nominalAmount, Double cryptoPrice, Double dollarExchange, User user, CryptoQuote quote) throws UserException {
        super(activeCrypto, nominalAmount, cryptoPrice, dollarExchange, user, quote);
    }

    public Boolean thePriceIsNotWithinTheAllowedLimit(CryptoQuote quote) {
        return quote.getPrice() > super.getCryptoPrice();
    }

    @Override
    public String shippingAddress() {
        return getUser().getWalletAddress();
    }

    @Override
    public String intentionType() {
        return "buy";
    }

    @Override
    public void validateOperation(User buyer) throws UserException {
        if (isOwner(buyer)){
            throw new UserException("cannot init transaction");
        }
    }

    @Override
    public void completeTransaction(Integer points, User buyer) {
        scorePoints(points, buyer);
        super.giveCrypto(getUser());
        super.drawCrypto(buyer);
    }

}
