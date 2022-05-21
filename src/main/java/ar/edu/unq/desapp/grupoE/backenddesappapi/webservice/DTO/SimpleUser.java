package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import org.springframework.context.annotation.Bean;

public class SimpleUser {
    private final String firstName;
    private final String lastName;
    private final Integer operationAmount;
    private final Integer reputation;

    public SimpleUser(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.operationAmount = user.getOperationsAmount();
        this.reputation = user.getReputation();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getOperationAmount() {
        return operationAmount;
    }

    public Integer getReputation() {
        return reputation;
    }

}
