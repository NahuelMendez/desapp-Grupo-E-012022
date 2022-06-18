package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import org.springframework.context.annotation.Bean;

public class SimpleUserDTO {
    private String firstName;
    private String lastName;
    private Integer operationAmount;
    private Integer reputation;

    public SimpleUserDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.operationAmount = user.getOperationsAmount();
        this.reputation = user.getReputation();
    }

    public SimpleUserDTO(){};

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
