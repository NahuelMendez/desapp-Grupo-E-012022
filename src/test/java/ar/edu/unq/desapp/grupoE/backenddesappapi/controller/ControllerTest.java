package ar.edu.unq.desapp.grupoE.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoE.backenddesappapi.service.CryptoQuoteService;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.IntentionDTO;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.IntentionResponseDTO;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.UserDTO;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.UserRegisterResponseDTO;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

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

    protected String baseURL() {return "http://localhost:" + port;}

    protected String urlUsers() { return baseURL() + "/api/users"; }

    protected UserDTO anyUser() {
        return new UserDTO("Example First Name", "Example Last Name", "default@gmail.com", "Default address 123", "DefaultValidPass22!!", "1234567891234567891234", "12345678");
    }

    protected ResponseEntity<UserRegisterResponseDTO> registerUser(UserDTO userRegisterDTO) {
        return restTemplate.postForEntity(
                urlUsers(),
                new HttpEntity<UserDTO>(userRegisterDTO),
                UserRegisterResponseDTO.class);
    }

    protected ResponseEntity<IntentionResponseDTO> expressIntention(IntentionDTO intention, UserRegisterResponseDTO user) {
        return restTemplate.postForEntity(
                urlUsers() + "/" + user.getId().toString() + "/intentions",
                new HttpEntity<IntentionDTO>(intention),
                IntentionResponseDTO.class);
    }

}
