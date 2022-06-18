package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class CryptoSellMovement extends CryptoMovement {

    public CryptoSellMovement() {
        super();
    };

    public CryptoSellMovement(String activeCrypto, Integer nominalAmount, LocalDate date) {
        super(activeCrypto, nominalAmount, date);
    }
}
