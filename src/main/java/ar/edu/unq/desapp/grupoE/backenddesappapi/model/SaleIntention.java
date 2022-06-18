package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.Entity;

@Entity
public class SaleIntention extends Intention {

    public SaleIntention(){super();}

    public SaleIntention(String activeCrypto, int nominalAmount, Double cryptoPrice, Double dollarExchange, User user, CryptoQuote quote) throws UserException {
        super(activeCrypto, nominalAmount, cryptoPrice, dollarExchange, user, quote);
    }

    @Override
    public Boolean thePriceIsNotWithinTheAllowedLimit(CryptoQuote quote) {
        return quote.getPrice() < super.getCryptoPrice();
    }

    @Override
    public String shippingAddress() {
        return getUser().getCvu();
    }

    @Override
    public String intentionType() {
        return "sale";
    }

    @Override
    public void validateOperation(User user) throws UserException {
        if (isOwner(user)){
            throw new UserException("cannot init transaction");
        }
    }

    @Override
    public void completeTransaction(Integer points, User buyer) {
        scorePoints(points, buyer);
        super.giveCrypto(buyer);
        super.drawCrypto(getUser());
    }
}
