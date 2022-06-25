package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TokenDTO {

    private String token;

    public TokenDTO(String token){};
}
