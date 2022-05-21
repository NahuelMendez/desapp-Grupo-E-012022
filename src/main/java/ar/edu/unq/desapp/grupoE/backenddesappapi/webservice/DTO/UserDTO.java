package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class UserDTO {

    public static final String USERNAME_ERROR_MESSAGE = "The name can't have less than 3 or more than 30 characters.";
    public static final String USER_EMAIL_ERROR_MESSAGE = "The email should have an email format, for example 'example@example.com'.";
    public static final String VALID_EMAIL_ADDRESS_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final String USER_ADDRESS_ERROR_MESSAGE = "The user address can't have less than 10 or more than 30 characters.";
    public static final String CVU_ERROR_MESSAGE = "The cvu should have 22 digits.";
    public static final String CONTAINS_ONLY_DIGITS_REGEX = "[0-9]+";
    public static final String WALLET_ADDRESS_ERROR_MESSAGE = "The wallet address should contains 8 digits.";
    public static final String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#?!@$%^&-]).{6,}";
    public static final String PASSWORD_ERROR_MESSAGE = "The password is not valid, must cointains a lowecase, a uppercase, a special character and minimum 6 in length";
    public static final String CANNOT_BE_NULL = "cannot be null";
    public static final String THE_CVU_SHOULD_HAVE_ONLY_DIGITS = "The cvu should have only digits";
    public static final String THE_WALLET_ADDRESS_SHOULD_HAVE_ONLY_DIGITS = "The wallet address should have only digits";

    @NotNull(message = "first name " + CANNOT_BE_NULL)
    @Length(min = 3, max = 30, message = USERNAME_ERROR_MESSAGE)
    private String firstName;

    @NotNull(message = "last name " + CANNOT_BE_NULL)
    @Length(min = 3, max = 30, message = USERNAME_ERROR_MESSAGE)
    private String lastName;

    @NotNull(message = "email " + CANNOT_BE_NULL)
    @Email(message = USER_EMAIL_ERROR_MESSAGE)
    private String email;

    @NotNull(message = "address " + CANNOT_BE_NULL)
    @Length(min = 10, max = 30, message = USER_ADDRESS_ERROR_MESSAGE)
    private String address;

    @NotNull(message = "password " + CANNOT_BE_NULL)
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_ERROR_MESSAGE)
    private String password;

    @NotNull(message = "cvu " + CANNOT_BE_NULL)
    @Length(min = 22, max = 22, message = CVU_ERROR_MESSAGE)
    @Pattern(regexp = CONTAINS_ONLY_DIGITS_REGEX, message = THE_CVU_SHOULD_HAVE_ONLY_DIGITS)
    private String cvu;

    @NotNull(message = "wallet address " + CANNOT_BE_NULL)
    @Length(min = 8, max = 8, message = WALLET_ADDRESS_ERROR_MESSAGE)
    @Pattern(regexp = CONTAINS_ONLY_DIGITS_REGEX, message = THE_WALLET_ADDRESS_SHOULD_HAVE_ONLY_DIGITS)
    private String walletAddress;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws UserException {
        this.password = password;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User createUser() throws UserException {
        return new User(firstName,  lastName,  email,  address,  password,  cvu,  walletAddress);
    }
}
