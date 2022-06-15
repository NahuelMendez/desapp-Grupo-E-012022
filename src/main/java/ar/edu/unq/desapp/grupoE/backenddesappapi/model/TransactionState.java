package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
abstract public class TransactionState {

    @Id
    @GeneratedValue
    private Integer id;

    public abstract void confirmedTransfer(Transaction transaction, LocalDateTime completeDate) throws UserException;

    public abstract void successfulTransfer(Transaction transaction) throws UserException;

    public abstract void cancelOperation(User user, Transaction transaction) throws UserException;
}
