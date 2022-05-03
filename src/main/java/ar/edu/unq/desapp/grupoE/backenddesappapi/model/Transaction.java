package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class Transaction {

    public static final String CANNOT_CONFIRM_TRANSFER = "cannot confirm transfer";
    public static final String CANNOT_MADE_TRANSFER = "cannot made transfer";
    private TransactionState status;
    private LocalDateTime date;
    private User buyer;
    private User seller;
    private Intention intention;

    public Transaction(LocalDateTime date, User buyer, User seller, Intention intention){
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
        this.intention = intention;
        this.status = new StartedState();
    }

    public void cancelOperation(User user) {
        user.cancelTransaction();
        this.status = new CanceledState();
    }

    public Boolean isWithin30Minutes(LocalDateTime completeDate) {
        return Math.abs(date.getMinute() - completeDate.getMinute()) <= 30
                && date.getHour() <= completeDate.getHour()
                && date.getDayOfMonth() == completeDate.getDayOfMonth();
    }

    public void successfulTransfer() throws UserException {
        status.successfulTransfer(this);
    }

    public void madeTransfer() {
        this.status = new TransferStatus();
    }

    public void confirmedTransfer(LocalDateTime completeDate) throws UserException {
        status.confirmedTransfer(this, completeDate);
    }

    public void completeTransaction(LocalDateTime completeDate) {
        if (this.isWithin30Minutes(completeDate)) {
            completeTransactionForBoth( 10);
        } else {
            completeTransactionForBoth( 5);
        }
    }

    private void completeTransactionForBoth( Integer points) {
        buyer.completeTransaction(points);
        seller.completeTransaction(points);
    }

    public void cancelCompleteTransaction() throws UserException {
        throw new UserException(CANNOT_CONFIRM_TRANSFER);
    }

    public void cancelTransfer() throws UserException {
        throw new UserException(CANNOT_MADE_TRANSFER);
    }
}
