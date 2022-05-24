package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

public class UserBuilder {

    public static UserBuilder anUser(){
        return new UserBuilder();
    }

    private String firstName = "Example First Name";
    private String lastName = "Example Last Name";
    private String email = "default@gmail.com";
    private String address = "Default address 123";
    private String password = "DefaultValidPass22!!";
    private String cvu = "1234567891234567891234";
    private String walletAddress = "12345678";

    public User build() throws UserException {
        return new User(firstName, lastName, email, address, password, cvu, walletAddress);
    }

    public UserBuilder withFirstName(final String firstName){
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(final String lastName){
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withEmail(final String email){
        this.email = email;
        return this;
    }

    public UserBuilder withAddress(final String address){
        this.address = address;
        return this;
    }

    public UserBuilder withPassword(final String password){
        this.password = password;
        return this;
    }

    public UserBuilder withCVU(final String cvu){
        this.cvu = cvu;
        return this;
    }

    public UserBuilder withWalletAddress(final String walletAddress){
        this.walletAddress = walletAddress;
        return this;
    }

}
