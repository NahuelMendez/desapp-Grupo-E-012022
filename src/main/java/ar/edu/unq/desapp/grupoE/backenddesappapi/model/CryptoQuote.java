package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cryptos")
public class CryptoQuote {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @Column
    private Double price;
    @Column
    private LocalDateTime updateTime;

    public CryptoQuote(String name, Double price, LocalDateTime updateTime) {
        this.name = name;
        this.price = price;
        this.updateTime = updateTime;
    }

    public CryptoQuote(){super();}

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
