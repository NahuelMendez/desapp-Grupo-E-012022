package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CryptoTest {

    @Test
    public void ACryptoHasANameAPriceAndUpdateTime() {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        CryptoQuote cryptoQuote = new CryptoQuote("ALICEUSDT", 100f, date);

        assertEquals("ALICEUSDT", cryptoQuote.getName());
        assertEquals(100, cryptoQuote.getPrice());
        assertEquals(date, cryptoQuote.getUpdateTime());
    }
}
