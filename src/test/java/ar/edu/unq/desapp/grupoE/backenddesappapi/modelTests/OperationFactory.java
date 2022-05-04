package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class OperationFactory {

    public static List<Crypto> updatedQuotes() {
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        return Collections.singletonList(crypto);
    }

    public static List<Crypto> quotesWithCryptoPrice(Integer price) {
        Crypto crypto = new Crypto("ALICEUSDT", price, LocalDateTime.now());
        return Collections.singletonList(crypto);
    }

    public static PurchaseIntention aPurchaseIntention(User user, List<Crypto> quotes, Integer price) throws UserException {
        return new PurchaseIntention("ALICEUSDT", 200, price, 5000, user, quotes);
    }

    public static SaleIntention aSaleIntention(User user, List<Crypto> quotes, Integer price) throws UserException {
        return new SaleIntention("ALICEUSDT", 200, price, 5000, user, quotes);
    }

}
