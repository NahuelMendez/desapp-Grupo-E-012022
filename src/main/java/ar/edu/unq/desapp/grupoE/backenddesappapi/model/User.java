package ar.edu.unq.desapp.grupoE.backenddesappapi.model;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

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
    @OneToOne(targetEntity=OperationIntent.class, fetch=FetchType.EAGER)
    private OperationIntent intention;
    @Column
    private int points = 0;
    @Column
    private int operationsAmount = 0;
    @Column
    private String walletAddress;


    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String address, String password, String cvu, String walletAddress) {

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

    public void expressIntention(OperationIntent opIntent) {
        intention = opIntent;
    }

    public OperationIntent getIntention() {
        return intention;
    }

    public String shippingAddress() {
        return intention.shippingAddress(this);
    }

    public void cancelOperation() {
        points = points - 20;
    }

    public int getReputation() {
        return points;
    }

    public int operationsAmount() {
        return operationsAmount;
    }

    private void addPoints(int pointsToAdd) {
        points = points + pointsToAdd;
    }

    void addCompleteTransaction(int points) {
        this.addPoints(points);
        operationsAmount++;
    }

}
