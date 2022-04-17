package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class Transaction {

    private LocalDateTime date;
    private User buyer;
    private User seller;

    public Transaction(LocalDateTime date, User buyer, User seller){
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
    }

    public Boolean isWithin30Minutes(LocalDateTime completeDate) {
        return Math.abs(date.getMinute() - completeDate.getMinute()) <= 30
                && date.getHour() <= completeDate.getHour()
                && date.getDayOfMonth() == completeDate.getDayOfMonth();
    }

    public void completeTransaction(LocalDateTime completeDate) {
        if (this.isWithin30Minutes(completeDate)) {
            buyer.addCompleteTransaction(10);
            seller.addCompleteTransaction(10);
        } else {
            buyer.addCompleteTransaction(5);
            seller.addCompleteTransaction(5);
        }
        OperationIntent intention = seller.getIntention();
        Asset asset = new Asset(intention.getActiveCrypto(), intention.getNominalAmount());
        buyer.addAsset(asset);
        seller.addAsset(asset);
    }
}
