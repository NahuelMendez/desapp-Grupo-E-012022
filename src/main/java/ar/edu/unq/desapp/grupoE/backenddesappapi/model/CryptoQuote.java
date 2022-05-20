package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class CryptoQuote {
    private String name;
    private Float price;
    private LocalDateTime updateTime;

    public CryptoQuote(String name, Float price, LocalDateTime updateTime) {
        this.name = name;
        this.price = price;
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
