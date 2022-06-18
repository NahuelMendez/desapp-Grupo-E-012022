package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Transaction {

    public static final String CANNOT_CONFIRM_TRANSFER = "cannot confirm transfer";
    public static final String CANNOT_MADE_TRANSFER = "cannot made transfer";
    public static final String CANNOT_CANCEL_TRANSACTION = "cannot cancel transaction";
    public static final String CANNOT_INIT_TRANSACTION = "cannot init transaction";
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne(cascade=CascadeType.ALL)
    private TransactionState status;
    @Column
    private LocalDateTime date;
    @OneToOne(cascade=CascadeType.ALL)
    private User buyer;
    @OneToOne(cascade=CascadeType.ALL)
    private Intention intention;

    public Transaction(){}

    public Transaction(LocalDateTime date, User buyer, Intention intention) throws UserException {
        intention.validateOperation(buyer);
        intention.disable();
        this.date = date;
        this.buyer = buyer;
        this.intention = intention;
        this.status = new StartedState();
    }

    public void cancelOperation(User user) throws UserException {
        status.cancelOperation(user, this);
    }

    public void confirmCancelOperation(User user) throws UserException {
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
        this.status = new TransferState();
    }

    public void confirmTransferFor(User user, LocalDateTime completeDate, CryptoQuote quote) throws UserException {
        this.assertUserIsIntentionOwner(user);
        this.confirmTransfer(completeDate, quote);
    }

    public void completeTransaction(LocalDateTime completeDate) {
        this.status = new CompleteTransactionState();
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

    private void assertUserIsIntentionOwner(User user) throws UserException {
        if (isNotOwnerIntention(user)) {
            this.throwConfirmTransferException();
        }
    }

    private boolean isNotOwnerIntention(User user) {
        return !intention.isOwner(user);
    }

    private void assertUserIsParticipant(User user) throws UserException {
        if (isNotBuyer(user) && isNotOwnerIntention(user)) {
            throw new UserException(CANNOT_CANCEL_TRANSACTION);
        }
    }

    private boolean isNotBuyer(User user) {
        return buyer != user;
    }

    private void completeTransactionForBoth( Integer points) {
        /*buyer.completeTransaction(points);
        intention.getUser().completeTransaction(points);*/
        intention.completeTransaction(points, buyer);
    }

    public Intention getIntention() {
        return intention;
    }

    public void throwCancelOperation() throws UserException {
        throw new UserException(CANNOT_CANCEL_TRANSACTION);
    }
}
