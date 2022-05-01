package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDTO {

    public static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#?!@$%^&-]).{6,}", Pattern.CASE_INSENSITIVE);
    public static final String PASSWORD_ERROR_MESSAGE = "The password is not valid, must cointains a lowecase, a uppercase, a special character and minimum 6 in length";

    @NotNull(message = "firstName cannot be null")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    private String lastName;

    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "address cannot be null")
    private String address;

    @NotNull(message = "password cannot be null")
    private String password;

    @NotNull(message = "cvu cannot be null")
    private String cvu;

    @NotNull(message = "walletAddress cannot be null")
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
//        validatePassword(password);
        this.password = password;
    }

//    private void validatePassword(String password) throws UserException {
//        if (!isValidPassword(password)) {
//            throw new UserException(PASSWORD_ERROR_MESSAGE);
//        }
//    }
//
//    private boolean isValidPassword(String password) {
//        Matcher matcher = PASSWORD_REGEX.matcher(password);
//        return matcher.matches();
//    }

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
