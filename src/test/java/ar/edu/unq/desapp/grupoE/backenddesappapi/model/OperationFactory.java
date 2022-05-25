package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;
import java.util.Collections;

import static ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserBuilder.anUser;

public class OperationFactory {

    public static CryptoQuote updatedQuote() {
        return new CryptoQuote("ALICEUSDT", 120d, LocalDateTime.now());
    }

    public static CryptoQuote quoteWithCryptoPrice(Double price) {
        return new CryptoQuote("ALICEUSDT", price, LocalDateTime.now());
    }

    public static PurchaseIntention aPurchaseIntention(User user, CryptoQuote quote, Double price) throws UserException {
        return new PurchaseIntention("ALICEUSDT", 200, price, 5000, user, quote);
    }

    public static SaleIntention aSaleIntention(User user, CryptoQuote quote, Double price) throws UserException {
        return new SaleIntention("ALICEUSDT", 200, price, 5000, user, quote);
    }

    public static User getUserWithPurchaseIntention() throws UserException {
        User user = anUser().build();
        PurchaseIntention purchase = aPurchaseIntention(user, updatedQuote(), 120d);
        user.expressIntention(purchase);
        return user;
    }

    public static User getUserWithSaleIntention() throws UserException {
        User user = anUser().build();
        Intention sale = aSaleIntention(user, updatedQuote(), 120d); ;
        user.expressIntention(sale);
        return user;
    }

}
