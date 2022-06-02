package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

 class Casa {

    String compra;

    public Casa(String compra) {
        this.compra = compra;
    }

    public Casa() {}

    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }
}

public class DollarQuoteResponse {

    Casa casa;

    public DollarQuoteResponse(Casa casa) {
        this.casa = casa;
    }

    public DollarQuoteResponse() {}

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }
}
