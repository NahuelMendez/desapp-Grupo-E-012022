package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests.OperationFactory.*;
import static ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests.UserBuilder.anUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntentionTest {

    @Test
    public void anIntentionHasCryptoNominalAmountCryptoPriceAndOperationAmount() throws UserException {
        User user = anUser().build();
        List<CryptoQuote> quotes = updatedQuotes();
        Intention intention = aSaleIntention(user, quotes, 120);

        assertEquals("ALICEUSDT", intention.getActiveCrypto());
        assertEquals(200, intention.getNominalAmount());
        assertEquals(120, intention.getCryptoPrice());
        assertEquals(5000, intention.getOperationAmount());
    }

    @Test
    public void aSaleIntentionGetCvuAddress() throws UserException {
        User user = anUser().build();
        List<CryptoQuote> quotes = updatedQuotes();
        Intention intention = aSaleIntention(user, quotes, 120);

        String cvuAddress = user.getCvu();

        assertEquals(cvuAddress, intention.shippingAddress());
    }

    @Test
    public void aPurchaseIntentionGetWalletAddress() throws UserException {
        User user = anUser().build();
        List<CryptoQuote> quotes = updatedQuotes();
        Intention intention = aPurchaseIntention(user, quotes, 120);
        String walletAddress = user.getWalletAddress();

        assertEquals(walletAddress , intention.shippingAddress());
    }

    @Test
    public void anIntentionWithPriceOutsideTheVariationMarginOfFivePercent() throws UserException {
        User user = anUser().build();
        List<CryptoQuote> quotes = updatedQuotes();

        assertThrowCannotCreateIntention(user, quotes, 127);
        assertThrowCannotCreateIntention(user, quotes, 113);
    }

    private void assertThrowCannotCreateIntention(User user, List<CryptoQuote> quotes, int price) {
        UserException thrownPurchase = assertThrows(UserException.class, () -> {
            aPurchaseIntention(user, quotes, price);
        });

        assertEquals(Intention.CANNOT_CREATE_INTENTION, thrownPurchase.getMessage());
    }

}
