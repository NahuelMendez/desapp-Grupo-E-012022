package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class Transaction {

    private LocalDateTime date;
    private User buyer;
    private User seller;
    private Intention intention;

    public Transaction(LocalDateTime date, User buyer, User seller, Intention intention){
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
        this.intention = intention;
    }

    public void cancelOperation(User user) {
        user.cancelTransaction();
    }

    public Boolean isWithin30Minutes(LocalDateTime completeDate) {
        return Math.abs(date.getMinute() - completeDate.getMinute()) <= 30
                && date.getHour() <= completeDate.getHour()
                && date.getDayOfMonth() == completeDate.getDayOfMonth();
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

    public void successfulTransfer() {
    }

    public void confirmedTransfer() {

    }
}
