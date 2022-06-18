package ar.edu.unq.desapp.grupoE.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionControllerTest extends ControllerTest{

    private String urlNewTrasaction(Integer intentionId, Integer userId) { return baseURL() + "/api/transactions/" + intentionId + "/users/" + userId; }

    private UserRegisterResponseDTO intentionOwner;
    private UserRegisterResponseDTO transactionOwner;

    @BeforeEach
    public void setUp(){
        intentionOwner = getRegisteredUser(anyUser());
        transactionOwner = getRegisteredUser(anyUser());
    }

    @Test
    public void whenCreateATrasactionTheResponseBodyHasATransactionWithStartedStateStatus() {
        String symbol = "ALICEUSDT";
        Double price = 220d;
        CryptoQuote cryptoQuote = new CryptoQuote(symbol, price, LocalDateTime.now());
        Mockito.when(cryptoQuoteService.getCryptoQuote(symbol)).thenReturn(cryptoQuote);

        UserDTO userRegisterDTO = anyUser();
        UserDTO anotherUserDTO = anyUser();

        IntentionDTO intention = new IntentionDTO(symbol, 200, price, "sale");
        UserRegisterResponseDTO user = getRegisteredUser(userRegisterDTO);
        UserRegisterResponseDTO anotherUser = getRegisteredUser(anotherUserDTO);

        IntentionResponseDTO expressedIntention = expressIntention(intention, user).getBody();
        ResponseEntity<TransactionDTO> transaction = createTransaction(expressedIntention.getId(), anotherUser.getId());
        TransactionDTO responseBody = transaction.getBody();

        assertEquals(transaction.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getUser().getFirstName(), user.getFirstName());
        assertEquals(responseBody.getSymbol(), expressedIntention.getCrypto());
        assertEquals(responseBody.getShippingAddress(), userRegisterDTO.getCvu());
        assertEquals(responseBody.getCryptoPrice(), intention.getCryptoPrice());
        assertEquals(responseBody.getNominalAmount(), intention.getNominalAmount());
        assertNotNull(responseBody.getOperationAmount());
    }

    @Test
    public void whenCreateAWrongTrasactionTheResponseIsABadRequest() {
        String symbol = "ALICEUSDT";
        Double price = 220d;
        CryptoQuote cryptoQuote = new CryptoQuote(symbol, price, LocalDateTime.now());
        Mockito.when(cryptoQuoteService.getCryptoQuote(symbol)).thenReturn(cryptoQuote);

        UserDTO userRegisterDTO = anyUser();

        IntentionDTO intention = new IntentionDTO(symbol, 200, price, "sale");
        UserRegisterResponseDTO user = getRegisteredUser(userRegisterDTO);

        IntentionResponseDTO expressedIntention = expressIntention(intention, user).getBody();
        ResponseEntity<TransactionDTO> transaction = createTransaction(expressedIntention.getId(), user.getId());

        assertEquals(transaction.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void whenATransactionIsMarkedAsPaidTheResponseIsASuccesfulMessage() {
        ResponseEntity<TransactionDTO> transaction = createTransaction(intentionOwner, transactionOwner);
        TransactionDTO responseBody = transaction.getBody();

        /*ResponseEntity<String> paidMessage = restTemplate.put(
                baseURL() + "/api/transactions/" + responseBody.getId() + "/users/" + transactionOwner.getId() + "/paid}",
                HttpEntity.EMPTY
        );*/

        // assertEquals(paidMessage.getStatusCode(), HttpStatus.OK);
    }

    private ResponseEntity<TransactionDTO> createTransaction(UserRegisterResponseDTO intentionOwner, UserRegisterResponseDTO transactionOwner) {
        String symbol = "ALICEUSDT";
        Double price = 220d;
        CryptoQuote cryptoQuote = new CryptoQuote(symbol, price, LocalDateTime.now());
        Mockito.when(cryptoQuoteService.getCryptoQuote(symbol)).thenReturn(cryptoQuote);

        IntentionDTO intention = new IntentionDTO(symbol, 200, price, "sale");

        IntentionResponseDTO expressedIntention = expressIntention(intention, intentionOwner).getBody();
        ResponseEntity<TransactionDTO> transaction = createTransaction(expressedIntention.getId(), transactionOwner.getId());
        return transaction;
    }

    private UserRegisterResponseDTO getRegisteredUser(UserDTO userRegisterDTO) {
        return registerUser(userRegisterDTO).getBody();
    }

    private ResponseEntity<TransactionDTO> createTransaction(Integer intentionId, Integer userId) {
        return restTemplate.postForEntity(
                urlNewTrasaction(intentionId, userId),
                HttpEntity.EMPTY,
                TransactionDTO.class
        );
    }

}
