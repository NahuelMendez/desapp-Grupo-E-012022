package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;

public class UserResponse {

    private final String firstName;
    private final String lastName;
    private final Integer operationsAmount;
    private final Integer reputation;

    public UserResponse(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.operationsAmount = user.getOperationsAmount();
        this.reputation = user.getReputation();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getOperationsAmount() {
        return operationsAmount;
    }

    public Integer getReputation() {
        return reputation;
    }
}
