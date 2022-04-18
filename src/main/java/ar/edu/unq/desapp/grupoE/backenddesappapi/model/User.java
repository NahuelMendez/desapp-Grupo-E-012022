package ar.edu.unq.desapp.grupoE.backenddesappapi.model;


public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String password;
    private String cvu;
    private OperationIntent intention;
    private int points = 0;
    private int operationsAmount = 0;
    private String walletAddress;

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

    public String getShippingAddress() {
        return intention.getShippingAddress(this);
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
