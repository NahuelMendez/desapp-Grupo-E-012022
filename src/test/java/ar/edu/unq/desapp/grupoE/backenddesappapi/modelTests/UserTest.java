package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;




public class UserTest {

    public User anUser() {
        return new User("Pepe", "Pepa", "email@gmail.com", "San Martin 185", "unaPassword", "1234567891234567891234", "12345678");
    }

    private User getUserWithPurchaseIntention() {
        User user = anUser();
        PurchaseIntent purchase = new PurchaseIntent("ALICEUSDT", 200, 120, 5000);
        user.expressIntention(purchase);
        return user;
    }

    private User getUserWithSaleIntention(String cryptoName, int nominalAmount) {
        User user = anUser();
        SaleIntent sale = new SaleIntent(cryptoName, nominalAmount, 120, 5000);
        user.expressIntention(sale);
        return user;
    }

    @Test
    public void anUserHasNameLastNameEmailAddressPasswordCVUAndWalletAddress() {
        User user = anUser();

        assertEquals("Pepe", user.getFirstName());
        assertEquals("Pepa", user.getLastName());
        assertEquals("email@gmail.com", user.getEmail());
        assertEquals("San Martin 185", user.getAddress());
        assertEquals("unaPassword", user.getPassword());
        assertEquals("1234567891234567891234", user.getCvu());
        assertEquals("12345678", user.getWalletAddress());

    }

    @Test
    public void anUserExpressHisPurchaseIntent() {
        User user = getUserWithPurchaseIntention();

        OperationIntent intention = user.getIntention();

        assertEquals("ALICEUSDT", intention.getActiveCrypto());
        assertEquals(200, intention.getNominalAmount());
        assertEquals(120, intention.getCryptoPrice());
        assertEquals(5000, intention.getOperationAmount());
    }

    @Test
    public void anUserExpressHisSaleIntent() {
        User user = getUserWithSaleIntention("ALICEUSDT", 200);

        OperationIntent intention = user.getIntention();

        assertEquals("ALICEUSDT", intention.getActiveCrypto());
        assertEquals(200, intention.getNominalAmount());
        assertEquals(120, intention.getCryptoPrice());
        assertEquals(5000, intention.getOperationAmount());
    }

    @Test
    public void anUserWithSaleIntentionGetCvuAddress() {
        User user = getUserWithSaleIntention("ALICEUSDT", 200);

        String cvuAddress = user.getCvu();

        assertEquals(cvuAddress, user.getShippingAddress());
    }

    @Test
    public void anUserWithPurchaseIntentionGetWalletAddress() {
        User user = getUserWithPurchaseIntention();

        String walletAddress = user.getWalletAddress();

        assertEquals(walletAddress , user.getShippingAddress());
    }

    @Test
    public void whenAnTransactionIsCancelledTheUserLoseReputation(){
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        User user = anUser();
        User seller = getUserWithSaleIntention("ALICEUSDT", 200);

        user.startTransaction(seller, date);

        user.cancelOperation();

        assertEquals(-20, user.getReputation());
        assertEquals(0, user.operationsAmount());
    }

    @Test
    public void whenAnOperationIsCompleteWithin30MinutesTheUsersGets10ReputationPoints(){
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        User user = anUser();
        User seller = getUserWithSaleIntention("ALICEUSDT", 200);

        user.startTransaction(seller, date);

        user.confirmTransaction();
        seller.confirmTransaction();

        user.completeTransaction(seller, date);

        assertEquals(10, user.getReputation());
        assertEquals(10, seller.getReputation());
        assertEquals(1, user.operationsAmount());
        assertEquals(1, seller.operationsAmount());
    }

    @Test
    public void whenAnOperationIsNotCompleteWithin30MinutesTheUsersGets5ReputationPoints(){
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        LocalDateTime postDate = LocalDateTime.of(2022, 4, 16, 21, 50);
        User user = anUser();
        User seller = getUserWithSaleIntention("ALICEUSDT", 200);

        user.startTransaction(seller, date);

        user.confirmTransaction();
        seller.confirmTransaction();

        user.completeTransaction(seller, postDate);

        assertEquals(5, user.getReputation());
        assertEquals(5, seller.getReputation());
        assertEquals(1, user.operationsAmount());
        assertEquals(1, seller.operationsAmount());
    }

    @Test
    public void whenAUserMakesAPurchaseTransactionAcquiresTheAsset(){
        User user = anUser();
        completeAPurchaseTransaction(user, "ALICEUSDT", 200);

        assertTrue(user.assets()
                .stream()
                .anyMatch( asset -> asset.activeCrypto() == "ALICEUSDT"
                        && asset.nominalAmount() == 200));
    }

    @Test
    public void whenAUserMakesMoreThanOnePurchaseTransactionAcquiresTheAssets(){
        User user = anUser();
        completeAPurchaseTransaction(user, "ALICEUSDT", 200);
        completeAPurchaseTransaction(user, "MATICUSDT", 300);

        assertTrue(user.assets()
                .stream()
                .anyMatch( asset -> asset.activeCrypto() == "ALICEUSDT"
                        && asset.nominalAmount() == 200));
        assertTrue(user.assets()
                .stream()
                .anyMatch( asset -> asset.activeCrypto() == "MATICUSDT"
                        && asset.nominalAmount() == 300));
    }

    private void completeAPurchaseTransaction(User user, String cryptoName, int nominalAmount) {
        User seller = getUserWithSaleIntention(cryptoName, nominalAmount);
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        user.startTransaction(seller, date);

        user.confirmTransaction();
        seller.confirmTransaction();

        user.completeTransaction(seller, date);
    }

}
