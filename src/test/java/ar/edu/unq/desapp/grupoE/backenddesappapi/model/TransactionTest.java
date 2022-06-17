package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupoE.backenddesappapi.model.OperationFactory.*;
import static ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserBuilder.anUser;
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

        SaleIntention sale = aSaleIntention(seller, updatedQuote(), 125d);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer , sale);

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
        SaleIntention sale = aSaleIntention(seller, updatedQuote(), 120d);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, sale);

        UserException thrown = assertThrows(UserException.class,  () -> {
            transaction.confirmTransferFor(seller, date, updatedQuote());
        });

        assertEquals(Transaction.CANNOT_CONFIRM_TRANSFER, thrown.getMessage());
    }

    @Test
    public void theTransferCannotBeMadeIfTheTransactionWasCanceled() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = aSaleIntention(seller, updatedQuote(), 122d);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, sale);

        transaction.cancelOperation(buyer);

        UserException thrown = assertThrows(UserException.class, () ->
                transaction.doTransfer(buyer)
        );

        assertEquals(Transaction.CANNOT_MADE_TRANSFER, thrown.getMessage());
    }

    @Test
    public void whenAPurchaseOperationIsCompleteButTheSystemPriceIsAboveOfThePriceStatedByTheUser() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Intention sale = aPurchaseIntention(buyer, quoteWithCryptoPrice(120d), 120d);
        buyer.expressIntention(sale);

        Transaction transaction = new Transaction(date, seller, sale);

        transaction.doTransfer(seller);
        transaction.confirmTransferFor(buyer, date, quoteWithCryptoPrice(140d));

        assertEquals(0, buyer.getReputation());
        assertEquals(0, seller.getReputation());
        assertEquals(0, buyer.getOperationsAmount());
        assertEquals(0, seller.getOperationsAmount());
    }

    @Test
    public void whenASaleOperationIsCompleteButTheSystemPriceIsBelowOfThePriceStatedByTheUser() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Intention sale = aSaleIntention(seller, quoteWithCryptoPrice(120d), 120d);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, sale);

        transaction.doTransfer(buyer);
        transaction.confirmTransferFor(seller, date, quoteWithCryptoPrice(100d));

        assertEquals(0, buyer.getReputation());
        assertEquals(0, seller.getReputation());
        assertEquals(0, buyer.getOperationsAmount());
        assertEquals(0, seller.getOperationsAmount());
    }

    @Test
    public void sellerCannotIndicateThatMadeTheTransfer() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Intention sale = aSaleIntention(seller, quoteWithCryptoPrice(120d), 120d);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, sale);

        UserException thrown = assertThrows(UserException.class, () -> {
            transaction.doTransfer(seller);
        });

        assertEquals(Transaction.CANNOT_MADE_TRANSFER, thrown.getMessage());
    }

    @Test
    public void buyerCannotConfirmTheTransfer() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Intention sale = aSaleIntention(seller, quoteWithCryptoPrice(120d), 120d);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, sale);

        transaction.doTransfer(buyer);

        UserException thrown = assertThrows(UserException.class, () -> {
            transaction.confirmTransferFor(buyer, date, quoteWithCryptoPrice(100d));
        });

        assertEquals(Transaction.CANNOT_CONFIRM_TRANSFER, thrown.getMessage());
    }

    @Test
    public void AnUserWhoDoesNotParticipatesInTheTransactionCannotCancelIt() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Intention sale = aSaleIntention(seller, quoteWithCryptoPrice(120d), 120d);
        seller.expressIntention(sale);
        User otherUser = anUser().build();

        Transaction transaction = new Transaction(date, buyer, sale);

        UserException thrown = assertThrows(UserException.class, () -> {
            transaction.cancelOperation(otherUser);
        });

        assertEquals(Transaction.CANNOT_CANCEL_TRANSACTION, thrown.getMessage());
    }

    @Test
    public void APurchaseTransactionMustBeGeneratedFromSaleIntention() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Intention sale = aPurchaseIntention(buyer, quoteWithCryptoPrice(120d), 120d);
        buyer.expressIntention(sale);

        UserException thrown = assertThrows(UserException.class, () -> {
            new Transaction(date, buyer, sale);
        });

        assertEquals(Transaction.CANNOT_INIT_TRANSACTION, thrown.getMessage());
    }

    @Test
    public void ASaleTransactionMustBeGeneratedFromPurchaseIntention() throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Intention sale = aSaleIntention(seller, quoteWithCryptoPrice(120d), 120d);
        seller.expressIntention(sale);

        UserException thrown = assertThrows(UserException.class, () -> {
            new Transaction(date, seller, sale);
        });

        assertEquals(Transaction.CANNOT_INIT_TRANSACTION, thrown.getMessage());
    }

    private void doTransactionWithin30Minutes( User buyer, User seller) throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        SaleIntention sale = aSaleIntention(seller, updatedQuote(), 120d);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, sale);

        transaction.doTransfer(buyer);
        transaction.confirmTransferFor(seller, date, updatedQuote());
    }

    private void doTransactionPassingThe30Minutes(User buyer, User seller) throws UserException {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        LocalDateTime postDate = LocalDateTime.of(2022, 4, 16, 21, 50);
        SaleIntention sale = aSaleIntention(seller, updatedQuote(), 120d);
        seller.expressIntention(sale);

        Transaction transaction = new Transaction(date, buyer, sale);

        transaction.doTransfer(buyer);
        transaction.confirmTransferFor(seller, postDate, updatedQuote());
    }

}
