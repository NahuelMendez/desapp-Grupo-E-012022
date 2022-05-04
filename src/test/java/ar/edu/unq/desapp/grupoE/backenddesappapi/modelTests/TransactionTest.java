package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests.OperationFactory.*;
import static ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests.UserBuilder.anUser;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private User buyer;
    private User seller;

    @BeforeEach
    public void init() throws UserException {
        this.buyer = anUser().build();
        this.seller = anUser().build();
    }

    @Test
    public void whenAnOperationIsCompleteWithin30MinutesTheUsersGets10ReputationPoints() throws UserException {
        doTransactionWithin30Minutes( buyer, seller);

        assertEquals(10, buyer.getReputation());
        assertEquals(10, seller.getReputation());
        assertEquals(1, buyer.getOperationsAmount());
        assertEquals(1, seller.getOperationsAmount());
    }

    @Test
    public void whenAnOperationIsNotCompleteWithin30MinutesTheUsersGets5ReputationPoints() throws UserException {
        doTransactionPassingThe30Minutes(buyer, seller);

        assertEquals(5, buyer.getReputation());
        assertEquals(5, seller.getReputation());
        assertEquals(1, buyer.getOperationsAmount());
        assertEquals(1, seller.getOperationsAmount());
    }

    @Test
    public void whenAnTransactionIsCancelledTheUserLoseReputation() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);

        SaleIntention sale = aSaleIntention(seller, updatedQuotes(), 125);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer ,seller, sale);

        transaction.cancelOperation(buyer);

        assertEquals(-20, buyer.getReputation());
        assertEquals(0, buyer.getOperationsAmount());
    }

    @Test
    public void whenAnUserCompleteTwoTransactionYourReputationIsCalculatedForPointsAndOperationsAmount() throws UserException {
        doTransactionWithin30Minutes(buyer, seller);
        doTransactionWithin30Minutes(buyer, seller);

        assertEquals(10, buyer.getReputation());
        assertEquals(10, seller.getReputation());
        assertEquals(2, buyer.getOperationsAmount());
        assertEquals(2, seller.getOperationsAmount());
    }

    @Test
    public void theTransferCannotBeConfirmedIfTheTransferWasNotMade() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = aSaleIntention(seller, updatedQuotes(), 120);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        UserException thrown = assertThrows(UserException.class,  () -> {
            transaction.confirmedTransfer(date, updatedQuotes());
        });

        assertEquals(Transaction.CANNOT_CONFIRM_TRANSFER, thrown.getMessage());
    }

    @Test
    public void theTransferCannotBeMadeIfTheTransactionWasCanceled() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = aSaleIntention(seller, updatedQuotes(), 122);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.cancelOperation(buyer);

        UserException thrown = assertThrows(UserException.class, transaction::successfulTransfer);

        assertEquals(Transaction.CANNOT_MADE_TRANSFER, thrown.getMessage());
    }

    @Test
    public void whenAPurchaseOperationIsCompleteButTheSystemPriceIsAboveOfThePriceStatedByTheUser() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Intention sale = aPurchaseIntention(seller, quotesWithCryptoPrice(120), 120);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(date, quotesWithCryptoPrice(140));

        assertEquals(0, buyer.getReputation());
        assertEquals(0, seller.getReputation());
        assertEquals(0, buyer.getOperationsAmount());
        assertEquals(0, seller.getOperationsAmount());
    }

    @Test
    public void whenASaleOperationIsCompleteButTheSystemPriceIsBelowOfThePriceStatedByTheUser() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Intention sale = aSaleIntention(seller, quotesWithCryptoPrice(120), 120);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(date, quotesWithCryptoPrice(100));

        assertEquals(0, buyer.getReputation());
        assertEquals(0, seller.getReputation());
        assertEquals(0, buyer.getOperationsAmount());
        assertEquals(0, seller.getOperationsAmount());
    }

    private void doTransactionWithin30Minutes( User buyer, User seller) throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = aSaleIntention(seller, updatedQuotes(), 120);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(date, updatedQuotes());
    }

    private void doTransactionPassingThe30Minutes(User buyer, User seller) throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        LocalDateTime postDate = LocalDateTime.of(2022, 4, 16, 21, 50);
        SaleIntention sale = aSaleIntention(seller, updatedQuotes(), 120);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, seller, sale);

        transaction.successfulTransfer();
        transaction.confirmedTransfer(postDate, updatedQuotes());
    }

}
