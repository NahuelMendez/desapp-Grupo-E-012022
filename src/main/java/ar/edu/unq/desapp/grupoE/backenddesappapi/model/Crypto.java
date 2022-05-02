package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class Crypto {
    private String name;
    private Integer price;
    private LocalDateTime updateTime;

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
