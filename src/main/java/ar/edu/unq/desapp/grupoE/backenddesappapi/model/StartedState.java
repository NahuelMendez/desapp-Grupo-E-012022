package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class StartedState extends TransactionState {

    @Override
    public void confirmedTransfer(Transaction transaction, LocalDateTime completeDate) throws UserException {
        transaction.cancelCompleteTransaction();
    }

    @Override
    public void successfulTransfer(Transaction transaction) {
        transaction.madeTransfer();
    }
}
