package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.SaleIntention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Transaction;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void whenAnOperationIsCompleteWithin30MinutesTheUsersGets10ReputationPoints() throws UserException {

        User buyer = anUser();
        User seller = anUser();
        doTransactionWithin30Minutes( buyer, seller);

        assertEquals(10, buyer.getReputation());
        assertEquals(10, seller.getReputation());
        assertEquals(1, buyer.getOperationsAmount());
        assertEquals(1, seller.getOperationsAmount());
    }

    @Test
    public void whenAnOperationIsNotCompleteWithin30MinutesTheUsersGets5ReputationPoints() throws UserException {
        User buyer = anUser();
        User seller = anUser();
        doTransactionPassingThe30Minutes(buyer, seller);

        assertEquals(5, buyer.getReputation());
        assertEquals(5, seller.getReputation());
        assertEquals(1, buyer.getOperationsAmount());
        assertEquals(1, seller.getOperationsAmount());
    }

    @Test
    public void whenAnTransactionIsCancelledTheUserLoseReputation() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        User buyer = anUser();
        User seller = anUser();

        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer ,seller, sale);

        transaction.cancelOperation(buyer);

        assertEquals(-20, buyer.getReputation());
        assertEquals(0, buyer.getOperationsAmount());
    }

    @Test
    public void whenAnUserCompleteTwoTransactionYourReputationIsCalculatedForPointsAndOperationsAmount() throws UserException {
        User buyer = anUser();
        User seller = anUser();
        doTransactionWithin30Minutes(buyer, seller);
        doTransactionWithin30Minutes(buyer, seller);

        assertEquals(10, buyer.getReputation());
        assertEquals(10, seller.getReputation());
        assertEquals(2, buyer.getOperationsAmount());
        assertEquals(2, seller.getOperationsAmount());
    }

    @Test
    public void theTransferCannotBeConfirmedIfTheTransferWasNotMade() throws UserException {
        User buyer = anUser();
        User seller = anUser();

        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        UserException thrown = assertThrows(UserException.class,  () -> {
            transaction.confirmedTransfer(date);
        });

        assertEquals(Transaction.CANNOT_CONFIRM_TRANSFER, thrown.getMessage());
    }

    @Test
    public void theTransferCannotBeMadeIfTheTransactionWasCanceled() throws UserException {
        User buyer = anUser();
        User seller = anUser();

        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.cancelOperation(buyer);

        UserException thrown = assertThrows(UserException.class, transaction::successfulTransfer);

        assertEquals(Transaction.CANNOT_MADE_TRANSFER, thrown.getMessage());
    }

    private void doTransactionWithin30Minutes( User buyer, User seller) throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(date);
    }

    private void doTransactionPassingThe30Minutes(User buyer, User seller) throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        LocalDateTime postDate = LocalDateTime.of(2022, 4, 16, 21, 50);
        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(postDate);
    }

    public User anUser() throws UserException {
        return new User("Pepe", "Pepa", "email@gmail.com", "San Martin 185", "unaPassw123??", "1234567891234567891234", "12345678");
    }
}
