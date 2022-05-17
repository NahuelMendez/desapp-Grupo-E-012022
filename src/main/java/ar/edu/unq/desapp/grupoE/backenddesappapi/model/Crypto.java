package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cryptos")
public class Crypto {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer price;
    @Column
    private LocalDateTime updateTime;

    public Crypto(){super();}

    public Crypto(String name, Integer price, LocalDateTime updateTime) {
        this.name = name;
        this.price = price;
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
