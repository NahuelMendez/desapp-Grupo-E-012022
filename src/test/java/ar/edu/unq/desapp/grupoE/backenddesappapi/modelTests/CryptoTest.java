package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Crypto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CryptoTest {

    @Test
    public void ACryptoHasANameAPriceAndUpdateTime() {
        LocalDateTime date = LocalDateTime.of(2022, 4, 16, 21, 10);
        Crypto crypto = new Crypto("ALICEUSDT", 100, date);

        assertEquals("ALICEUSDT", crypto.getName());
        assertEquals(100, crypto.getPrice());
        assertEquals(date, crypto.getUpdateTime());
    }
}
