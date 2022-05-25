package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.time.LocalDateTime;

public class CryptoQuote {
    private String name;
    private Double price;
    private LocalDateTime updateTime;

    public CryptoQuote(String name, Double price, LocalDateTime updateTime) {
        this.name = name;
        this.price = price;
        this.updateTime = updateTime;
    }

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
