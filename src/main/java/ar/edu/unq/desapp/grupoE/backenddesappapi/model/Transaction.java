package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class Transaction {

    private LocalDateTime date;
    private User buyer;
    private User seller;
    private OperationIntent intention;

    public Transaction(LocalDateTime date, User buyer, User seller, OperationIntent intention){
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
        this.intention = intention;
    }

    public void cancelOperation(User buyer) {
        buyer.cancelOperation();
    }

    public Boolean isWithin30Minutes(LocalDateTime completeDate) {
        return Math.abs(date.getMinute() - completeDate.getMinute()) <= 30
                && date.getHour() <= completeDate.getHour()
                && date.getDayOfMonth() == completeDate.getDayOfMonth();
    }

    public void completeTransaction(LocalDateTime completeDate) {
        Asset asset = new Asset(intention.getActiveCrypto(), intention.getNominalAmount());
        if (this.isWithin30Minutes(completeDate)) {
            completeTransactionForBoth(asset, 10);
        } else {
            completeTransactionForBoth(asset, 5);
        }
    }

    private void completeTransactionForBoth(Asset asset, Integer points) {
        buyer.addCompleteTransaction(points);
        seller.addCompleteTransaction(points);
    }

    public void successfulTransfer() {
    }

    public void confirmedTransfer() {

    }
}
