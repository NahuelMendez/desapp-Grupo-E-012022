package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class Transaction {

    public static final String CANNOT_CONFIRM_TRANSFER = "cannot confirm transfer";
    public static final String CANNOT_MADE_TRANSFER = "cannot made transfer";
    public static final String CANNOT_CANCEL_TRANSFER = "cannot cancel transfer";
    private TransactionState status;
    private LocalDateTime date;
    private User buyer;
    private User seller;
    private Intention intention;

    public Transaction(LocalDateTime date, User buyer, User seller, Intention intention){
        intention.disable();
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
        this.intention = intention;
        this.status = new StartedState();
    }

    public void cancelOperation(User user) throws UserException {
        this.assertUserIsParticipant(user);
        user.subtractReputationPoints();
        intention.activate();
        this.status = new CanceledState();
    }

    public Boolean isWithin30Minutes(LocalDateTime completeDate) {
        return Math.abs(date.getMinute() - completeDate.getMinute()) <= 30
                && date.getHour() <= completeDate.getHour()
                && date.getDayOfMonth() == completeDate.getDayOfMonth();
    }

    public void doTransfer(User user) throws UserException {
        this.assertUserIsBuyer(user);
        status.successfulTransfer(this);
    }

    public void madeTransfer() {
        this.status = new TransferStatus();
    }

    public void confirmTransferFor(User user, LocalDateTime completeDate, CryptoQuote quote) throws UserException {
        this.assertUserIsSeller(user);
        this.confirmTransfer(completeDate, quote);
    }

    public void completeTransaction(LocalDateTime completeDate) {
        if (this.isWithin30Minutes(completeDate)) {
            completeTransactionForBoth( 10);
        } else {
            completeTransactionForBoth( 5);
        }
    }

    public void throwConfirmTransferException() throws UserException {
        throw new UserException(CANNOT_CONFIRM_TRANSFER);
    }

    public void throwMadeTransferException() throws UserException {
        throw new UserException(CANNOT_MADE_TRANSFER);
    }

    private void confirmTransfer(LocalDateTime completeDate, CryptoQuote quote) throws UserException {
        if (this.theSystemPriceIsAboveOfThePriceStatedByTheUser(quote)) {
            this.status = new CanceledState();
        }else {
            status.confirmedTransfer(this, completeDate);
        }
    }

    private boolean theSystemPriceIsAboveOfThePriceStatedByTheUser(CryptoQuote quote) {
        return intention.thePriceIsNotWithinTheAllowedLimit(quote);
    }

    private void assertUserIsBuyer(User user) throws UserException {
        if (isNotBuyer(user)) {
            this.throwMadeTransferException();
        }
    }

    private void assertUserIsSeller(User user) throws UserException {
        if (isNotSeller(user)) {
            this.throwConfirmTransferException();
        }
    }

    private void assertUserIsParticipant(User user) throws UserException {
        if (isNotBuyer(user) && isNotSeller(user)) {
            throw new UserException(CANNOT_CANCEL_TRANSFER);
        }
    }

    private boolean isNotSeller(User user) {
        return seller != user;
    }

    private boolean isNotBuyer(User user) {
        return buyer != user;
    }

    private void completeTransactionForBoth( Integer points) {
        buyer.completeTransaction(points);
        seller.completeTransaction(points);
    }
}
