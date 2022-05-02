package ar.edu.unq.desapp.grupoE.backenddesappapi.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;

@Entity
@Table(name = "users")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class User {

    public static final String USERNAME_ERROR_MESSAGE = "The name can't have less than 3 or more than 30 characters.";
    public static final String USER_EMAIL_ERROR_MESSAGE = "The email should have an email format, for example 'example@example.com'.";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String USER_ADDRESS_ERROR_MESSAGE = "The user address can't have less than 10 or more than 30 characters.";
    public static final String CVU_ERROR_MESSAGE = "The cvu should have 22 digits.";
    public static final Pattern CONTAINS_ONLY_DIGITS_REGEX = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
    public static final String WALLET_ADDRESS_ERROR_MESSAGE = "The wallet address should contains 8 digits.";
    public static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#?!@$%^&-]).{6,}", Pattern.CASE_INSENSITIVE);
    public static final String PASSWORD_ERROR_MESSAGE = "The password is not valid, must cointains a lowecase, a uppercase, a special character and minimum 6 in length";


    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String password;
    @Column
    private String cvu;
    @Column
    private int points = 0;
    @Column
    private int operationsAmount = 0;
    @Column
    private String walletAddress;
    @OneToMany(targetEntity= Intention.class, fetch=FetchType.EAGER) //Revisar
    private ArrayList<Intention> intentions;
    private Integer badPointsReputation = 0;

    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String address, String password, String cvu, String walletAddress) throws UserException {

        validateName(firstName, lastName);
        validateEmail(email);
        validateAddress(address);
        validatePassword(password);
        validateCvu(cvu);
        validateWalletAddress(walletAddress);

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.walletAddress = walletAddress;
        this.intentions = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getCvu() {
        return cvu;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public Integer getReputation() {
        if (operationsAmount == 0) {
            return badPointsReputation;
        }
        return points / operationsAmount + badPointsReputation;
    }

    public Integer getOperationsAmount() {
        return operationsAmount;
    }

    public void expressIntention(Intention intention) {
        intentions.add(intention);
    }

    public void cancelTransaction() {
        badPointsReputation = badPointsReputation - 20;
    }

    public void completeTransaction(int points) {
        this.addPoints(points);
        operationsAmount++;
    }

    public List<Intention> getIntentions() {
        return intentions;
    }

    private void addPoints(int pointsToAdd) {
        points = points + pointsToAdd;
    }

    private void validatePassword(String password) throws UserException {
        if (!isValidPassword(password)) {
            throw new UserException(PASSWORD_ERROR_MESSAGE);
        }
    }

    private boolean isValidPassword(String password) {
        Matcher matcher = PASSWORD_REGEX.matcher(password);
        return matcher.matches();
    }

    private void validateWalletAddress(String walletAddress) throws UserException {
        if (!(walletAddress.length() == 8) || !containsDigits(walletAddress)) {
            throw new UserException(WALLET_ADDRESS_ERROR_MESSAGE);
        }
    }

    private Boolean containsDigits(String userData) {
        Matcher matcher = CONTAINS_ONLY_DIGITS_REGEX.matcher(userData);
        return matcher.matches();
    }

    private void validateCvu(String cvu) throws UserException {
        if (!(cvu.length() == 22) || !containsDigits(cvu)) {
            throw new UserException(CVU_ERROR_MESSAGE);
        }
    }

    private void validateAddress(String address) throws UserException {
        if (address.length() > 30 || address.length() < 10) {
            throw new UserException(USER_ADDRESS_ERROR_MESSAGE);
        }
    }

    private void validateEmail(String email) throws UserException {
        if(!isValidEmail(email)) {
            throw new UserException(USER_EMAIL_ERROR_MESSAGE);
        }
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private void validateName(String firstName, String lastName) throws UserException {
        if (notValidName(firstName) || notValidName(lastName)) {
            throw new UserException(USERNAME_ERROR_MESSAGE);
        }
    }

    private Boolean notValidName(String firstName) {
        return firstName.length() < 3 || firstName.length() > 30;
    }
}
