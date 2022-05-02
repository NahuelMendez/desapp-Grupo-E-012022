package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntentionTest {

    @Test
    public void anIntentionHasCryptoNominalAmountCryptoPriceAndOperationAmount() throws UserException {
        Intention intention = new SaleIntention("ALICEUSDT", 200, 120, 5000, anUser());

        assertEquals("ALICEUSDT", intention.getActiveCrypto());
        assertEquals(200, intention.getNominalAmount());
        assertEquals(120, intention.getCryptoPrice());
        assertEquals(5000, intention.getOperationAmount());
    }

    @Test
    public void anSaleIntentionGetCvuAddress() throws UserException {
        User user = anUser();
        Intention intention = new SaleIntention("ALICEUSDT", 200, 120, 5000, user);

        String cvuAddress = user.getCvu();

        assertEquals(cvuAddress, intention.shippingAddress());
    }

    @Test
    public void anPurchaseIntentionGetWalletAddress() throws UserException {
        User user = anUser();
        Intention intention = new PurchaseIntention("ALICEUSDT", 200, 120, 5000, user);
        String walletAddress = user.getWalletAddress();

        assertEquals(walletAddress , intention.shippingAddress());
    }

    public User anUser() throws UserException {
        return new User("Pepe", "Pepa", "email@gmail.com", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
    }

    private String getValidPassword() {
        return "unaPassw123??";
    }
}
