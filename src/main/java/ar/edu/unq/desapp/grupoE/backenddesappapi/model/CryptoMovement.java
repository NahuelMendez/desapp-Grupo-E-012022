package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CryptoMovement {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String activeCrypto;
    @Column
    private Integer nominalAmount;
    @Column
    private LocalDate date;

    public CryptoMovement(String activeCrypto, Integer nominalAmount, LocalDate date) {
        this.activeCrypto = activeCrypto;
        this.nominalAmount = nominalAmount;
        this.date = date;
    }

    public CryptoMovement() {

    }

    public String getActiveCrypto() {
        return activeCrypto;
    }

    public Integer getNominalAmount() {
        return nominalAmount;
    }
}
