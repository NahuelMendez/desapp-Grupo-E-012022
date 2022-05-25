package ar.edu.unq.desapp.grupoE.backenddesappapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    protected int port;

    @Autowired
    TestRestTemplate restTemplate;

    protected String baseURL() {return "http://localhost:" + port;}

    protected String urlUsers() { return baseURL() + "/api/users"; }

    @Test
    void getAllUsersReturnsResponsesStatusOK() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                urlUsers(),
                List.class);

        List body = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(body.size(), 2);
    }
}
