package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests.UserBuilder.anUser;

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
