package ar.edu.unq.desapp.grupoE.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.IntentionDTO;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.IntentionResponseDTO;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.UserDTO;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.UserRegisterResponseDTO;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.ResponseBadRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends ControllerTest{

    @Test
    void getAllUsersReturnsResponsesStatusOKAndUsersList() {
        ResponseEntity<List> response = getAllUsers();

        List body = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(body.size(), 2);
    }

    @Test
    void registerUserReturnsStatusOK() {
        UserDTO userRegisterDTO = anyUser();

        ResponseEntity<UserRegisterResponseDTO> response = registerUser(userRegisterDTO);

        UserRegisterResponseDTO userResponse = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(userResponse.getId());
        assertEquals(userResponse.getFirstName(), userRegisterDTO.getFirstName());
        assertEquals(userResponse.getLastName(), userRegisterDTO.getLastName());
        assertEquals(userResponse.getEmail(), userRegisterDTO.getEmail());
    }

    @Test
    void registerUserWithInvalidDataReturnsStatusBadRequestAndErrorMessage() {
        UserDTO userRegisterDTO = anyUser();
        userRegisterDTO.setCvu("1234");

        ResponseEntity<ResponseBadRequest> response = registerUserBadRequest(userRegisterDTO);

        ResponseBadRequest responseBadRequest = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseBadRequest.getMessage(), "The cvu should have 22 digits.");
    }

    @Test
    void userExpressIntentionReturnsStatusOk() {
        String symbol = "ALICEUSDT";
        Double price = 220d;
        CryptoQuote cryptoQuote = new CryptoQuote(symbol, price, LocalDateTime.now());
        Mockito.when(cryptoQuoteService.getCryptoQuote(symbol)).thenReturn(cryptoQuote);

        UserDTO userRegisterDTO = anyUser();

        IntentionDTO intention = new IntentionDTO(symbol, 200, price, "sale");
        UserRegisterResponseDTO user = registerUser(userRegisterDTO).getBody();
        ResponseEntity<IntentionResponseDTO> response = expressIntention(intention, user);

        Mockito.verify(cryptoQuoteService).getCryptoQuote(symbol);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private ResponseEntity<List> getAllUsers() {
        return restTemplate.getForEntity(
                urlUsers(),
                List.class);
    }

    private ResponseEntity<ResponseBadRequest> registerUserBadRequest(UserDTO userRegisterDTO) {
        return restTemplate.postForEntity(
                urlUsers(),
                new HttpEntity<UserDTO>(userRegisterDTO),
                ResponseBadRequest.class);
    }
}
