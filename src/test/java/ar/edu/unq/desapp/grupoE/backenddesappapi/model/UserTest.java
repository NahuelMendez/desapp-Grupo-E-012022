package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

import org.junit.jupiter.api.Test;
import java.util.List;

import static ar.edu.unq.desapp.grupoE.backenddesappapi.model.OperationFactory.getUserWithPurchaseIntention;
import static ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserBuilder.anUser;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void anUserHasNameLastNameEmailAddressPasswordCVUAndWalletAddress() throws UserException {
        User user = anUser().build();

        assertEquals("Example First Name", user.getFirstName());
        assertEquals("Example Last Name", user.getLastName());
        assertEquals("default@gmail.com", user.getEmail());
        assertEquals("Default address 123", user.getAddress());
        assertEquals("DefaultValidPass22!!", user.getPassword());
        assertEquals("1234567891234567891234", user.getCvu());
        assertEquals("12345678", user.getWalletAddress());

    }

    @Test
    public void anUserNotHaveAPurchaseIntention() throws UserException {
        User user = anUser().build();

        List<Intention> intentions = user.getIntentions();

        assertTrue(intentions.isEmpty());
    }

    @Test
    public void anUserExpressHisIntention() throws UserException {
        User user = getUserWithPurchaseIntention();

        List<Intention> intentions = user.getIntentions();

        assertFalse(intentions.isEmpty());
    }

}
