package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class CryptoBuyingMovement extends CryptoMovement {

    public CryptoBuyingMovement() {
        super();
    };

    public CryptoBuyingMovement(String activeCrypto, Integer nominalAmount, LocalDate date) {
        super(activeCrypto, nominalAmount, date);
    }
}
