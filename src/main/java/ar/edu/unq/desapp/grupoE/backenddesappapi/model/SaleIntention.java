package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class SaleIntention extends Intention {

    public SaleIntention(){super();}

    public SaleIntention(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount, User user, List<Crypto> quotes) throws UserException {
        super(activeCrypto, nominalAmount, cryptoPrice, operationAmount, user, quotes);
    }

    @Override
    public Boolean thePriceIsNotWithinTheAllowedLimit(List<Crypto> quotes) {
        Crypto crypto = super.cryptoWithName(quotes, super.getActiveCrypto());
        return crypto.getPrice() < super.getCryptoPrice();
    }

    @Override
    public String shippingAddress() {
        return getUser().getCvu();
    }

    @Override
    public String intentionType() {
        return "sale";
    }
}
