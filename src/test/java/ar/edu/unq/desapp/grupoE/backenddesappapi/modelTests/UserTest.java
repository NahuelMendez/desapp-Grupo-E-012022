package ar.edu.unq.desapp.grupoE.backenddesappapi.modelTests;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    public User anUser() throws UserException {
        return new User("Pepe", "Pepa", "email@gmail.com", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
    }

    private String getValidPassword() {
        return "unaPassw123??";
    }

    private User getUserWithPurchaseIntention() throws UserException {
        User user = anUser();
        PurchaseIntent purchase = new PurchaseIntent("ALICEUSDT", 200, 120, 5000);
        user.expressIntention(purchase);
        return user;
    }

    private User getUserWithSaleIntention(String cryptoName, int nominalAmount) throws UserException {
        User user = anUser();
        SaleIntent sale = new SaleIntent(cryptoName, nominalAmount, 120, 5000);
        user.expressIntention(sale);
        return user;
    }

    @Test
    public void anUserHasNameLastNameEmailAddressPasswordCVUAndWalletAddress() throws UserException {
        User user = anUser();

        assertEquals("Pepe", user.getFirstName());
        assertEquals("Pepa", user.getLastName());
        assertEquals("email@gmail.com", user.getEmail());
        assertEquals("San Martin 185", user.getAddress());
        assertEquals("unaPassw123??", user.getPassword());
        assertEquals("1234567891234567891234", user.getCvu());
        assertEquals("12345678", user.getWalletAddress());

    }

    @Test
    public void anUserExpressHisPurchaseIntent() throws UserException {
        User user = getUserWithPurchaseIntention();

        OperationIntent intention = user.getIntention();

        assertEquals("ALICEUSDT", intention.getActiveCrypto());
        assertEquals(200, intention.getNominalAmount());
        assertEquals(120, intention.getCryptoPrice());
        assertEquals(5000, intention.getOperationAmount());
    }

    @Test
    public void anUserExpressHisSaleIntent() throws UserException {
        User user = getUserWithSaleIntention("ALICEUSDT", 200);

        OperationIntent intention = user.getIntention();

        assertEquals("ALICEUSDT", intention.getActiveCrypto());
        assertEquals(200, intention.getNominalAmount());
        assertEquals(120, intention.getCryptoPrice());
        assertEquals(5000, intention.getOperationAmount());
    }

    @Test
    public void anUserWithSaleIntentionGetCvuAddress() throws UserException {
        User user = getUserWithSaleIntention("ALICEUSDT", 200);

        String cvuAddress = user.getCvu();

        assertEquals(cvuAddress, user.shippingAddress());
    }

    @Test
    public void anUserWithPurchaseIntentionGetWalletAddress() throws UserException {
        User user = getUserWithPurchaseIntention();

        String walletAddress = user.getWalletAddress();

        assertEquals(walletAddress , user.shippingAddress());
    }

    @Test
    public void aUserNameShouldntHaveLessThan3Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("Ho", "Pepa", "email@gmail.com", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
        });

        assertEquals(User.USERNAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void aUserNameShouldntHaveMoreThan30Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("QWERTYUIOPASDFGHJKLÑZXCVBNMQWERTYUIOPASDFG", "Pepa", "email@gmail.com", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
        });

        assertEquals(User.USERNAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void aUserLastNameShouldntHaveLessThan3Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("Pepe", "Ho", "email@gmail.com", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
        });

        assertEquals(User.USERNAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void aUserLastNameShouldntHaveMoreThan30Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("Pepita", "QWERTYUIOPASDFGHJKLÑZXCVBNMQWERTYUIOPASDFG", "email@gmail.com", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
        });

        assertEquals(User.USERNAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserShouldHaveAValidEmail() {
        UserException withoutArroba = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepin", "emailgmail.com", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
        });

        UserException withoutDot = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepin", "email@gmailcom", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
        });

        UserException without = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepin", "email@.com", "San Martin 185", getValidPassword(), "1234567891234567891234", "12345678");
        });

        assertEquals(User.USER_EMAIL_ERROR_MESSAGE, withoutArroba.getMessage());
        assertEquals(User.USER_EMAIL_ERROR_MESSAGE, withoutDot.getMessage());
        assertEquals(User.USER_EMAIL_ERROR_MESSAGE, without.getMessage());
    }

    @Test
    public void anUserAddressShouldntHaveMoreThan30Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepi", "email@gmail.com", "QWERTYUIOPASDFGHJKLÑZXCVBNMQWERTYUIOPASDFG", getValidPassword(), "1234567891234567891234", "12345678");
        });

        assertEquals(User.USER_ADDRESS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserAddressShouldntHaveLessThan10Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepi", "email@gmail.com", "QWERT", getValidPassword(), "1234567891234567891234", "12345678");
        });

        assertEquals(User.USER_ADDRESS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserCVUShouldntHaveMoreThan22Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepi", "email@gmail.com", "QWERTERTYQWERTYT", getValidPassword(), "123456789123456789123456789", "12345678");
        });

        assertEquals(User.CVU_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserCVUShouldHaveOnlyDigits() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepi", "email@gmail.com", "QWERTERTYQWERTYT", getValidPassword(), "1234567a9123456789123b", "12345678");
        });

        assertEquals(User.CVU_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserWalletAddressShouldntHaveMoreThan8Length() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepi", "email@gmail.com", "QWERTERTYQWERTYT", getValidPassword(), "1234567891234567891234", "145678");
        });

        assertEquals(User.WALLET_ADDRESS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserWalletAddressShouldHaveOnlyDigits() {
        UserException thrown = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepi", "email@gmail.com", "QWERTERTYQWERTYT", getValidPassword(), "1234567891234567891234", "123AV678");
        });

        assertEquals(User.WALLET_ADDRESS_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void anUserShouldHaveAValidPassword() {
        UserException withoutLoweCase = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepin", "email@gmail.com", "San Martin 185", "PASSWORD??", "1234567891234567891234", "12345678");
        });

        UserException withoutUpperCase = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepin", "email@gmail.com", "San Martin 185", "unapassword", "1234567891234567891234", "12345678");
        });

        UserException withoutSpecialCharacter = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepin", "email@gmail.com", "San Martin 185", "unaPassword", "1234567891234567891234", "12345678");
        });

        UserException without6Length = assertThrows(UserException.class, () -> {
            new User("Pepita", "Pepin", "email@gmail.com", "San Martin 185", "paSS?", "1234567891234567891234", "12345678");
        });

        assertEquals(User.PASSWORD_ERROR_MESSAGE, withoutLoweCase.getMessage());
        assertEquals(User.PASSWORD_ERROR_MESSAGE, withoutUpperCase.getMessage());
        assertEquals(User.PASSWORD_ERROR_MESSAGE, withoutSpecialCharacter.getMessage());
        assertEquals(User.PASSWORD_ERROR_MESSAGE, without6Length.getMessage());
    }

}
