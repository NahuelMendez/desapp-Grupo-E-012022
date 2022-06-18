package ar.edu.unq.desapp.grupoE.backenddesappapi.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "users")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {

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
    @OneToMany(targetEntity=Intention.class, fetch=FetchType.EAGER)
    private final List<Intention> intentions = new ArrayList<>();
    @OneToMany(targetEntity=CryptoMovement.class, cascade=CascadeType.ALL)
    private final List<CryptoMovement> cryptoMovements = new ArrayList<>();
    private Integer reputationPoints = 0;

    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String address, String password, String cvu, String walletAddress) throws UserException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.walletAddress = walletAddress;
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
            return reputationPoints;
        }
        return points / operationsAmount + reputationPoints;
    }

    public Integer getOperationsAmount() {
        return operationsAmount;
    }

    public void expressIntention(Intention intention) {
        intentions.add(intention);
    }

    public void subtractReputationPoints() {
        reputationPoints = reputationPoints - 20;
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

    public Integer getId() {
        return id;
    }

    public void addCryptoMovement(CryptoMovement cryptoMovement) {
        cryptoMovements.add(cryptoMovement);
    }
}
