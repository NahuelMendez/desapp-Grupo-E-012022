package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserBuilder.anUser;

public class OperationFactory {

    public static List<CryptoQuote> updatedQuotes() {
        CryptoQuote cryptoQuote = new CryptoQuote("ALICEUSDT", 120d, LocalDateTime.now());
        return Collections.singletonList(cryptoQuote);
    }

    public static List<CryptoQuote> quotesWithCryptoPrice(Double price) {
        CryptoQuote cryptoQuote = new CryptoQuote("ALICEUSDT", price, LocalDateTime.now());
        return Collections.singletonList(cryptoQuote);
    }

    public static PurchaseIntention aPurchaseIntention(User user, List<CryptoQuote> quotes, Integer price) throws UserException {
        return new PurchaseIntention("ALICEUSDT", 200, price, 5000, user, quotes);
    }

    public static SaleIntention aSaleIntention(User user, List<CryptoQuote> quotes, Integer price) throws UserException {
        return new SaleIntention("ALICEUSDT", 200, price, 5000, user, quotes);
    }

    public static User getUserWithPurchaseIntention() throws UserException {
        User user = anUser().build();
        PurchaseIntention purchase = aPurchaseIntention(user, updatedQuotes(), 120);
        user.expressIntention(purchase);
        return user;
    }

    public static User getUserWithSaleIntention() throws UserException {
        User user = anUser().build();
        Intention sale = aSaleIntention(user, updatedQuotes(), 120); ;
        user.expressIntention(sale);
        return user;
    }

}
