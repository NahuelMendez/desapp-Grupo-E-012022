package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "intentions")
public abstract class OperationIntent {

    @Id
    private Integer id;
    @Column
    private String activeCrypto;
    @Column
    private int nominalAmount;
    @Column
    private int cryptoPrice;
    @Column
    private int operationAmount;

    public OperationIntent(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount) {
        this.activeCrypto = activeCrypto;
        this.nominalAmount = nominalAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationAmount = operationAmount;
    }

    public String getActiveCrypto(){
        return activeCrypto;
    }

    public int getNominalAmount() {
        return nominalAmount;
    }

    public int getCryptoPrice() {
        return cryptoPrice;
    }

    public int getOperationAmount() {
        return operationAmount;
    }

    public abstract String shippingAddress(User user);
}
