package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "intentions")
public abstract class Intention {

    public static final String CANNOT_CREATE_INTENTION = "Cannot create intention";
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String activeCrypto;
    @Column
    private int nominalAmount;
    @Column
    private Double cryptoPrice;
    @Column
    private int operationAmount;
    @ManyToOne(fetch=FetchType.EAGER)
    private User user;
    @Column
    private Boolean active = true;

    public Intention(){super();}

    public Intention(String activeCrypto, int nominalAmount, Double cryptoPrice, int operationAmount, User user, CryptoQuote quote) throws UserException {
        assertPriceIsOutsideTheVariationMarginOfFivePercent(quote, cryptoPrice);
        this.activeCrypto = activeCrypto;
        this.nominalAmount = nominalAmount;
        this.cryptoPrice = cryptoPrice;
        this.operationAmount = operationAmount;
        this.user = user;
    }

    protected void assertPriceIsOutsideTheVariationMarginOfFivePercent(CryptoQuote quote, Double price) throws UserException {
        if ((quote.getPrice() * 0.05 <  Math.abs(price - quote.getPrice()))) {
            throw new UserException(CANNOT_CREATE_INTENTION);
        }
    }

    public abstract Boolean thePriceIsNotWithinTheAllowedLimit(CryptoQuote quote);

    public String getActiveCrypto(){
        return activeCrypto;
    }

    public int getNominalAmount() {
        return nominalAmount;
    }

    public Double getCryptoPrice() {
        return cryptoPrice;
    }

    public int getOperationAmount() {
        return operationAmount;
    }

    public User getUser() {
        return user;
    }

    public abstract String shippingAddress();

    public abstract String intentionType();

    public Boolean isActive() {
        return active;
    }

    public void disable() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }
}
