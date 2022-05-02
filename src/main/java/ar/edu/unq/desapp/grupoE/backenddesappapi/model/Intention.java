package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.*;

@Entity
@Table(name = "intentions")
public abstract class Intention {

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
    @ManyToOne
    private User user;

    public Intention(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount, User user) {
        this.activeCrypto = activeCrypto;
        this.nominalAmount = nominalAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationAmount = operationAmount;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public abstract String shippingAddress();


}
