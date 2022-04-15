package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String password;
    private String cvu;

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

    private String walletAddress;


}
