package ar.edu.unq.desapp.grupoE.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.CryptoQuoteProvider;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.CryptoQuoteService;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.IntentionDTO;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.UserDTO;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.UserRegisterResponse;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.ResponseBadRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @LocalServerPort
    protected int port;

    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    CryptoQuoteService cryptoQuoteService;

    protected String baseURL() {return "http://localhost:" + port;}

    protected String urlUsers() { return baseURL() + "/api/users"; }

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

        ResponseEntity<UserRegisterResponse> response = registerUser(userRegisterDTO);

        UserRegisterResponse userResponse = response.getBody();

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

        IntentionDTO intention = new IntentionDTO(symbol, 200, price, 5000, "sale");
        UserRegisterResponse user = registerUser(userRegisterDTO).getBody();
        ResponseEntity<IntentionDTO> response = restTemplate.postForEntity(
                urlUsers() + "/" + user.getId().toString() + "/intention",
                new HttpEntity<IntentionDTO>(intention),
                IntentionDTO.class);

        Mockito.verify(cryptoQuoteService).getCryptoQuote(symbol);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private ResponseEntity<List> getAllUsers() {
        return restTemplate.getForEntity(
                urlUsers(),
                List.class);
    }

    private ResponseEntity<UserRegisterResponse> registerUser(UserDTO userRegisterDTO) {
        return restTemplate.postForEntity(
                urlUsers(),
                new HttpEntity<UserDTO>(userRegisterDTO),
                UserRegisterResponse.class);
    }

    private ResponseEntity<ResponseBadRequest> registerUserBadRequest(UserDTO userRegisterDTO) {
        return restTemplate.postForEntity(
                urlUsers(),
                new HttpEntity<UserDTO>(userRegisterDTO),
                ResponseBadRequest.class);
    }

    private UserDTO anyUser() {
        return new UserDTO("Example First Name", "Example Last Name", "default@gmail.com", "Default address 123", "DefaultValidPass22!!", "1234567891234567891234", "12345678");
    }
}
