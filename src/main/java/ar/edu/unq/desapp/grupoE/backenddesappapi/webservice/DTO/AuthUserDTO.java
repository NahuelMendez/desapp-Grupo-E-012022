package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AuthUserDTO {

    private String email;
    private String password;

    public User createUser() throws UserException {
        return new User("firstName",  "lastName",  email,  "addressExample",  password,  "12341234!!",  "12341234123412341234");
    }

}
