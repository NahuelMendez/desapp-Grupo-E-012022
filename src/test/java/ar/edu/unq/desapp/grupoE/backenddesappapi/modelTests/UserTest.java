package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;

import static ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests.OperationFactory.getUserWithPurchaseIntention;
import static ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests.UserBuilder.anUser;
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

    @Test
    public void aUserNameShouldntHaveLessThan3Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withFirstName("Ho").build();
        });

        assertEquals(User.USERNAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void aUserNameShouldntHaveMoreThan30Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withFirstName("QWERTYUIOPASDFGHJKLÑZXCVBNMQWERTYUIOPASDFG").build();
        });

        assertEquals(User.USERNAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void aUserLastNameShouldntHaveLessThan3Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withLastName("Ho").build();
        });

        assertEquals(User.USERNAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void aUserLastNameShouldntHaveMoreThan30Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withLastName("QWERTYUIOPASDFGHJKLÑZXCVBNMQWERTYUIOPASDFG").build();
        });

        assertEquals(User.USERNAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserShouldHaveAValidEmail() {
        UserException withoutArroba = assertThrows(UserException.class, () -> {
            anUser().withEmail("emailgmail.com").build();
        });

        UserException withoutDot = assertThrows(UserException.class, () -> {
            anUser().withEmail("email@gmailcom").build();
        });

        UserException without = assertThrows(UserException.class, () -> {
            anUser().withEmail("email@.com").build();
        });

        assertEquals(User.USER_EMAIL_ERROR_MESSAGE, withoutArroba.getMessage());
        assertEquals(User.USER_EMAIL_ERROR_MESSAGE, withoutDot.getMessage());
        assertEquals(User.USER_EMAIL_ERROR_MESSAGE, without.getMessage());
    }

    @Test
    public void anUserAddressShouldntHaveMoreThan30Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withAddress("QWERTYUIOPASDFGHJKLÑZXCVBNMQWERTYUIOPASDFG").build();
        });

        assertEquals(User.USER_ADDRESS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserAddressShouldntHaveLessThan10Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withAddress("QWERT").build();
        });

        assertEquals(User.USER_ADDRESS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserCVUShouldntHaveMoreThan22Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withCVU("123456789123456789123456789").build();
        });

        assertEquals(User.CVU_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserCVUShouldHaveOnlyDigits() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withCVU("1234567a9123456789123b").build();
        });

        assertEquals(User.CVU_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserWalletAddressShouldntHaveMoreThan8Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withWalletAddress("145678").build();
        });

        assertEquals(User.WALLET_ADDRESS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserWalletAddressShouldHaveOnlyDigits() {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withWalletAddress("123AV678").build();
        });

        assertEquals(User.WALLET_ADDRESS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserShouldHaveAValidPassword() {
        assertThrowInvalidPasswordMessage("PASSWORD??");
        assertThrowInvalidPasswordMessage("unapassword");
        assertThrowInvalidPasswordMessage("unaPassword");
        assertThrowInvalidPasswordMessage("paSS?");
    }

    private void assertThrowInvalidPasswordMessage(String password) {
        UserException thrown = assertThrows(UserException.class, () -> {
            anUser().withPassword(password).build();
        });

        assertEquals(User.PASSWORD_ERROR_MESSAGE, thrown.getMessage());
    }

}
