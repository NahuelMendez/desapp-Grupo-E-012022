package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class TransferStatus extends TransactionState {

    @Override
    public void confirmedTransfer(Transaction transaction, LocalDateTime completeDate) throws UserException {
        transaction.completeTransaction(completeDate);
    }

    @Override
    public void successfulTransfer(Transaction transaction) throws UserException {
        transaction.cancelTransfer();
    }
}
