package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class CanceledState extends TransactionState {

    @Override
    public void confirmedTransfer(Transaction transaction, LocalDateTime completeDate) throws UserException {
        transaction.throwConfirmTransferException();
    }

    @Override
    public void successfulTransfer(Transaction transaction) throws UserException {
        transaction.throwMadeTransferException();
    }
}
