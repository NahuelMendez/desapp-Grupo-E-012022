package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.service.CryptoQuoteService;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.DollarQuoteService;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
abstract class ControllerTest {

    @LocalServerPort
    protected int port;

    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    CryptoQuoteService cryptoQuoteService;

    @MockBean
    DollarQuoteService dollarQuoteService;

    protected String token;

    protected String baseURL() {return "http://localhost:" + port;}

    protected String urlUsers() { return baseURL() + "/api/users"; }

    protected UserDTO anyUser() {
        return new UserDTO("Example First Name", "Example Last Name", "default@gmail.com", "Default address 123", "DefaultValidPass22!!", "1234567891234567891234", "12345678");
    }

    protected UserDTO anotherUser() {
        return new UserDTO("Example First Name 2", "Example Last Name", "default2@gmail.com", "Default address 000", "DefaultValidPass11!!", "1234567891234567891764", "12345612");
    }

    protected String getToken(AuthUserDTO authUserDTO) {
        return loginUser(authUserDTO).getHeaders().get("Authorization").get(0);
    }

    protected String getTokenForAnyUser(){
        AuthUserDTO authUser = new AuthUserDTO(anyUser().getEmail(), anyUser().getPassword());
        return getToken(authUser);
    }

    protected HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getTokenForAnyUser());
        return headers;
    }

    protected ResponseEntity<UserRegisterResponseDTO> registerUser(UserDTO userRegisterDTO) {
        ResponseEntity<UserRegisterResponseDTO> registeredUser = restTemplate.postForEntity(
                urlUsers(),
                new HttpEntity<UserDTO>(userRegisterDTO),
                UserRegisterResponseDTO.class);
        return registeredUser;
    }

    protected ResponseEntity<String> loginUser(AuthUserDTO authUserDTO) {
        ResponseEntity<String> response = restTemplate.postForEntity(
                baseURL() + "/api/login",
                new HttpEntity<AuthUserDTO>(authUserDTO),
                String.class);
        return response;
    }

    protected ResponseEntity<IntentionResponseDTO> expressIntention(IntentionDTO intention, UserRegisterResponseDTO user) {
        return restTemplate.postForEntity(
                urlUsers() + "/" + user.getId().toString() + "/intentions",
                new HttpEntity<IntentionDTO>(intention, getHttpHeaders()),
                IntentionResponseDTO.class);
    }

}
