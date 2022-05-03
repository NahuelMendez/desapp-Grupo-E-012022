package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "intentions")
public abstract class Intention {

    public static final String CANNOT_CREATE_INTENTION = "Cannot create intention";
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

    public Intention(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount, User user, List<Crypto> quotes) throws UserException {
        assertPriceIsOutsideTheVariationMarginOfFivePercent(quotes, activeCrypto, cryptoPrice);
        this.activeCrypto = activeCrypto;
        this.nominalAmount = nominalAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationAmount = operationAmount;
        this.user = user;
    }

    protected void assertPriceIsOutsideTheVariationMarginOfFivePercent(List<Crypto> quotes,String cryptoName,Integer price) throws UserException {
        Crypto crypto = quotes.stream().filter(quote -> cryptoName.equals(quote.getName())).findFirst().get();
        if ((crypto.getPrice() * 0.05 <  Math.abs(price - crypto.getPrice())) ||
                (crypto.getPrice() * 0.05 >  Math.abs(price - crypto.getPrice()))) {
            throw new UserException(CANNOT_CREATE_INTENTION);
        }
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
