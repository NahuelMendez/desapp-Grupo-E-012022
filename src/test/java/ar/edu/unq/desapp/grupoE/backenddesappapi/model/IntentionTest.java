package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import static ar.edu.unq.desapp.grupoE.backenddesappapi.model.OperationFactory.*;
import static ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserBuilder.anUser;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertTrue(intention.isActive());
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

    @Test
    public void WhenAnIntentionIsAddedToATransactionThenItIsDisable() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        User seller = anUser().build();
        User buyer = anUser().build();
        List<Crypto> quotes = updatedQuotes();
        Intention intention = aSaleIntention(seller, quotes, 120);

        new Transaction(date, buyer ,seller, intention);

        assertFalse(intention.isActive());
    }

    @Test
    public void WhenATransactionIsCanceledThenItIntentionIsActivated() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        User seller = anUser().build();
        User buyer = anUser().build();
        List<Crypto> quotes = updatedQuotes();
        Intention intention = aSaleIntention(seller, quotes, 120);
        Transaction transaction = new Transaction(date, buyer ,seller, intention);

        transaction.cancelOperation(seller);

        assertTrue(intention.isActive());
    }

}
