package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class TransferState extends TransactionState {

    @Override
    public void confirmedTransfer(Transaction transaction, LocalDateTime completeDate) {
        transaction.completeTransaction(completeDate);
    }

    @Override
    public void successfulTransfer(Transaction transaction) throws UserException {
        transaction.throwMadeTransferException();
    }

    @Override
    public void cancelOperation(User user, Transaction transaction) throws UserException {
        transaction.confirmCancelOperation(user);
    }
}
