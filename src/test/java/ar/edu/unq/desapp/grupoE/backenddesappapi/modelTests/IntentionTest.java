package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntentionTest {

    @Test
    public void anIntentionHasCryptoNominalAmountCryptoPriceAndOperationAmount() throws UserException {
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);
        Intention intention = new SaleIntention("ALICEUSDT", 200, 120, 5000, anUser(), quotes);

        assertEquals("ALICEUSDT", intention.getActiveCrypto());
        assertEquals(200, intention.getNominalAmount());
        assertEquals(120, intention.getCryptoPrice());
        assertEquals(5000, intention.getOperationAmount());
    }

    @Test
    public void aSaleIntentionGetCvuAddress() throws UserException {
        User user = anUser();
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);
        Intention intention = new SaleIntention("ALICEUSDT", 200, 120, 5000, user, quotes);

        String cvuAddress = user.getCvu();

        assertEquals(cvuAddress, intention.shippingAddress());
    }

    @Test
    public void aPurchaseIntentionGetWalletAddress() throws UserException {
        User user = anUser();
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);
        Intention intention = new PurchaseIntention("ALICEUSDT", 200, 120, 5000, user, quotes);
        String walletAddress = user.getWalletAddress();

        assertEquals(walletAddress , intention.shippingAddress());
    }

    @Test
    public void anIntentionWithPriceOutsideTheVariationMarginOfFivePercent() throws UserException {
        User user = anUser();
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);

        UserException thrownPurchase = assertThrows(UserException.class, () -> {
            new PurchaseIntention("ALICEUSDT", 200, 127, 5000, user, quotes);
        });

        UserException thrownSale = assertThrows(UserException.class, () -> {
            new SaleIntention("ALICEUSDT", 200, 113, 5000, user, quotes);
        });

        assertEquals(Intention.CANNOT_CREATE_INTENTION, thrownPurchase.getMessage());
        assertEquals(Intention.CANNOT_CREATE_INTENTION, thrownSale.getMessage());
    }

    public User anUser() throws UserException {
        return new User("Pepe", "Pepa", "email@gmail.com", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
    }

    private String getValidPassword() {
        return "unaPassw123??";
    }
}
