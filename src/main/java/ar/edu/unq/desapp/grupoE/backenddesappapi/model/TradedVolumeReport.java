package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import java.util.List;
import java.util.stream.Collectors;

public class TradedVolumeReport {

    private User user;
    private List<CryptoMovement> cryptoMovements;
    private List<CryptoQuote> allCryptoQuotes;
    private Double dollarQuote;

    public TradedVolumeReport(
            User user,
            List<CryptoMovement> cryptoMovements,
            List<CryptoQuote> allCryptoQuotes,
            Double dollarQuote) {
        this.user = user;
        this.cryptoMovements = cryptoMovements;
        this.allCryptoQuotes = allCryptoQuotes;
        this.dollarQuote = dollarQuote;
    }


    public User getUser() {
        return user;
    }

    public Double getTotalValueInUSD() {
        return getAssets()
                .stream()
                .map( cryptoReport -> cryptoReport.getPriceInUsd() * cryptoReport.getNominalAmount())
                .reduce( 0d, Double::sum);
    }

    public Double getTotalValueInPesos() {
        return getAssets()
                .stream()
                .map(cryptoReport -> cryptoReport.getPriceInPesos() * cryptoReport.getNominalAmount())
                .reduce( 0d, Double::sum);
    }

    public List<CryptoReport> getAssets() {
        return cryptoMovements
                .stream()
                .map(cryptoMovement ->
                    new CryptoReport(
                            cryptoMovement,
                            getCryptoQuote(cryptoMovement.getActiveCrypto()),
                            dollarQuote
                    )
                )
                .collect(Collectors.toList());
    }

    private CryptoQuote getCryptoQuote(String activeCrypto) {
        return allCryptoQuotes
                .stream()
                .filter(cryptoQuote -> cryptoQuote.getName().equals(activeCrypto))
                .findFirst()
                .get();
    }
}
