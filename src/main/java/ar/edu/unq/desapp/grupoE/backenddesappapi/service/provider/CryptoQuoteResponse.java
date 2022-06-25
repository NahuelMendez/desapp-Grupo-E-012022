package ar.edu.unq.desapp.grupoE.backenddesappapi.service.provider;

public class CryptoQuoteResponse {

    private String symbol;
    private String price;

    public CryptoQuoteResponse() {
    }

    public CryptoQuoteResponse(String symbol, String price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
