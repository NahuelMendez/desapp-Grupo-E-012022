package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void anUserHasNameLastNameEmailAddressPasswordCVUAndWalletAddress() {
        User user = new User("Pepe", "Pepa", "email@gmail.com", "San Martin 185", "unaPassword", "1234567891234567891234", "12345678");

        assertEquals("Pepe", user.getFirstName());
        assertEquals("Pepa", user.getLastName());
        assertEquals("email@gmail.com", user.getEmail());
        assertEquals("San Martin 185", user.getAddress());
        assertEquals("unaPassword", user.getPassword());
        assertEquals("1234567891234567891234", user.getCvu());
        assertEquals("12345678", user.getWalletAddress());

    }

    @Test
    public void anUserExpressHisPurchaseIntent() {

    }

}
