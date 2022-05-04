package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

abstract public class TransactionState {
    public abstract void confirmedTransfer(Transaction transaction, LocalDateTime completeDate) throws UserException;

    public abstract void successfulTransfer(Transaction transaction) throws UserException;
}
