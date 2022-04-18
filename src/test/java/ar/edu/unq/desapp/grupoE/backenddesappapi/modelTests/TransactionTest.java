package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.SaleIntent;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Transaction;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void whenAnOperationIsCompleteWithin30MinutesTheUsersGets10ReputationPoints(){
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        User buyer = anUser();
        User seller = getUserWithSaleIntention("ALICEUSDT", 200);

        Transaction transaction = new Transaction(date, buyer ,seller, seller.getIntention());

        transaction.successfulTransfer();
        transaction.confirmedTransfer();

        transaction.completeTransaction(date);

        assertEquals(10, buyer.getReputation());
        assertEquals(10, seller.getReputation());
        assertEquals(1, buyer.operationsAmount());
        assertEquals(1, seller.operationsAmount());
    }

    @Test
    public void whenAnOperationIsNotCompleteWithin30MinutesTheUsersGets5ReputationPoints(){
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        LocalDateTime postDate = LocalDateTime.of(2022, 4, 16, 21, 50);
        User buyer = anUser();
        User seller = getUserWithSaleIntention("ALICEUSDT", 200);

        Transaction transaction = new Transaction(date, buyer ,seller, seller.getIntention());

        transaction.successfulTransfer();
        transaction.confirmedTransfer();

        transaction.completeTransaction(postDate);

        assertEquals(5, buyer.getReputation());
        assertEquals(5, seller.getReputation());
        assertEquals(1, buyer.operationsAmount());
        assertEquals(1, seller.operationsAmount());
    }

    @Test
    public void whenAnTransactionIsCancelledTheUserLoseReputation(){
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        User buyer = anUser();
        User seller = getUserWithSaleIntention("ALICEUSDT", 200);

        Transaction transaction = new Transaction(date, buyer ,seller, seller.getIntention());

        transaction.cancelOperation(buyer);

        assertEquals(-20, buyer.getReputation());
        assertEquals(0, buyer.operationsAmount());
    }

    public User anUser() {
        return new User("Pepe", "Pepa", "email@gmail.com", "San Martin 185", "unaPassword", "1234567891234567891234", "12345678");
    }

    private User getUserWithSaleIntention(String cryptoName, int nominalAmount) {
        User user = anUser();
        SaleIntent sale = new SaleIntent(cryptoName, nominalAmount, 120, 5000);
        user.expressIntention(sale);
        return user;
    }
}
