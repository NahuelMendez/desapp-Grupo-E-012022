package ar.edu.unq.desapp.grupoE.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.*;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.ResponseBadRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends ControllerTest{

    @Test
    void getAllUsersReturnsResponsesStatusOKAndUsersList() {
        ResponseEntity<User[]> response = getAllUsers();

        User[] body = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(body.length, 2);
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
        Mockito.when(dollarQuoteService.getDollarQuote()).thenReturn(200d);

        UserDTO userRegisterDTO = anyUser();

        IntentionDTO intention = new IntentionDTO(symbol, 200, price, "sale");
        UserRegisterResponseDTO user = registerUser(userRegisterDTO).getBody();
        ResponseEntity<IntentionResponseDTO> response = expressIntention(intention, user);

        Mockito.verify(cryptoQuoteService).getCryptoQuote(symbol);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
    @Test
    void getAllIntentionsReturnsStatusOk() {
        String symbol = "ALICEUSDT";
        Double price = 220d;
        CryptoQuote cryptoQuote = new CryptoQuote(symbol, price, LocalDateTime.now());
        Mockito.when(cryptoQuoteService.getCryptoQuote(symbol)).thenReturn(cryptoQuote);
        Mockito.when(dollarQuoteService.getDollarQuote()).thenReturn(200d);

        UserDTO userRegisterDTO = anyUser();
        UserRegisterResponseDTO user = registerUser(userRegisterDTO).getBody();

        IntentionDTO saleIntention = new IntentionDTO(symbol, 200, price, "sale");
        IntentionDTO buyIntention = new IntentionDTO(symbol, 200, price, "buy");
        expressIntention(saleIntention, user);
        expressIntention(buyIntention, user);

        ResponseEntity<IntentionResponseDTO[]> response = getAllActiveIntentions();
        IntentionResponseDTO[] body = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(body.length, 2);
    }

    private ResponseEntity<IntentionResponseDTO[]> getAllActiveIntentions() {
        return restTemplate.getForEntity(
                baseURL() + "/api/intentions",
                IntentionResponseDTO[].class
        );
    }

    private ResponseEntity<User[]> getAllUsers() {
        return restTemplate.getForEntity(
                urlUsers(),
                User[].class
        );
    }

    private ResponseEntity<ResponseBadRequest> registerUserBadRequest(UserDTO userRegisterDTO) {
        return restTemplate.postForEntity(
                urlUsers(),
                new HttpEntity<UserDTO>(userRegisterDTO),
                ResponseBadRequest.class
        );
    }
}
