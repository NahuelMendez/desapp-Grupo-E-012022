package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);

        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller, quotes);
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
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller, quotes);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        UserException thrown = assertThrows(UserException.class,  () -> {
            transaction.confirmedTransfer(date, quotes);
        });

        assertEquals(Transaction.CANNOT_CONFIRM_TRANSFER, thrown.getMessage());
    }

    @Test
    public void theTransferCannotBeMadeIfTheTransactionWasCanceled() throws UserException {
        User buyer = anUser();
        User seller = anUser();
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller, quotes);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.cancelOperation(buyer);

        UserException thrown = assertThrows(UserException.class, transaction::successfulTransfer);

        assertEquals(Transaction.CANNOT_MADE_TRANSFER, thrown.getMessage());
    }

    @Test
    public void whenAPurchaseOperationIsCompleteButTheSystemPriceIsAboveOfThePriceStatedByTheUser() throws UserException {
        User buyer = anUser();
        User seller = anUser();
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        Crypto cryptoLastUpdate = new Crypto("ALICEUSDT", 140, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);
        List<Crypto> quotesLastUpdate = Collections.singletonList(cryptoLastUpdate);
        Intention sale = new PurchaseIntention("ALICEUSDT", 200, 120, 5000, seller, quotes);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(date, quotesLastUpdate);

        assertEquals(0, buyer.getReputation());
        assertEquals(0, seller.getReputation());
        assertEquals(0, buyer.getOperationsAmount());
        assertEquals(0, seller.getOperationsAmount());
    }

    @Test
    public void whenASaleOperationIsCompleteButTheSystemPriceIsBelowOfThePriceStatedByTheUser() throws UserException {
        User buyer = anUser();
        User seller = anUser();
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        Crypto cryptoLastUpdate = new Crypto("ALICEUSDT", 100, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);
        List<Crypto> quotesLastUpdate = Collections.singletonList(cryptoLastUpdate);
        Intention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller, quotes);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(date, quotesLastUpdate);

        assertEquals(0, buyer.getReputation());
        assertEquals(0, seller.getReputation());
        assertEquals(0, buyer.getOperationsAmount());
        assertEquals(0, seller.getOperationsAmount());
    }

    private void doTransactionWithin30Minutes( User buyer, User seller) throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);
        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller, quotes);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(date, quotes);
    }

    private void doTransactionPassingThe30Minutes(User buyer, User seller) throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        LocalDateTime postDate = LocalDateTime.of(2022, 4, 16, 21, 50);
        Crypto crypto = new Crypto("ALICEUSDT", 120, LocalDateTime.now());
        List<Crypto> quotes = Collections.singletonList(crypto);
        SaleIntention sale = new SaleIntention("ALICEUSDT", 200, 120, 5000, seller, quotes);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(postDate, quotes);
    }

    public User anUser() throws UserException {
        return new User("Pepe", "Pepa", "email@gmail.com", "San Martin 185", "unaPassw123??", "1234567891234567891234", "12345678");
    }
}
