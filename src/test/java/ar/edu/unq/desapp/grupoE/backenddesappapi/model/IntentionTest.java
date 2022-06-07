package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
        Intention intention = aSaleIntention(user, updatedQuote(), 120d);

        assertEquals("ALICEUSDT", intention.getActiveCrypto());
        assertEquals(200, intention.getNominalAmount());
        assertEquals(120, intention.getCryptoPrice());
        assertEquals(5000, intention.getDollarExchange());
        assertTrue(intention.isActive());
    }

    @Test
    public void aSaleIntentionGetCvuAddress() throws UserException {
        User user = anUser().build();
        Intention intention = aSaleIntention(user, updatedQuote(), 120d);

        String cvuAddress = user.getCvu();

        assertEquals(cvuAddress, intention.shippingAddress());
    }

    @Test
    public void aPurchaseIntentionGetWalletAddress() throws UserException {
        User user = anUser().build();
        Intention intention = aPurchaseIntention(user, updatedQuote(), 120d);
        String walletAddress = user.getWalletAddress();

        assertEquals(walletAddress , intention.shippingAddress());
    }

    @Test
    public void anIntentionWithPriceOutsideTheVariationMarginOfFivePercent() throws UserException {
        User user = anUser().build();
        CryptoQuote quote = updatedQuote();

        assertThrowCannotCreateIntention(user, quote, 127d);
        assertThrowCannotCreateIntention(user, quote, 113d);
    }

    private void assertThrowCannotCreateIntention(User user, CryptoQuote quote, Double price) {
        UserException thrownPurchase = assertThrows(UserException.class, () -> {
            aPurchaseIntention(user, quote, price);
        });

        assertEquals(Intention.CANNOT_CREATE_INTENTION, thrownPurchase.getMessage());
    }

    @Test
    public void WhenAnIntentionIsAddedToATransactionThenItIsDisable() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        User seller = anUser().build();
        User buyer = anUser().build();
        Intention intention = aSaleIntention(seller, updatedQuote(), 120d);

        new Transaction(date, buyer ,seller, intention);

        assertFalse(intention.isActive());
    }

    @Test
    public void WhenATransactionIsCanceledThenItIntentionIsActivated() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        User seller = anUser().build();
        User buyer = anUser().build();
        Intention intention = aSaleIntention(seller, updatedQuote(), 120d);
        Transaction transaction = new Transaction(date, buyer ,seller, intention);

        transaction.cancelOperation(seller);

        assertTrue(intention.isActive());
    }

}
