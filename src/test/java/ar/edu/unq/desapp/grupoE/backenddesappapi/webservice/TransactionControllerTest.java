package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionControllerTest extends ControllerTest{

    private String urlNewTransaction(Integer intentionId, Integer userId) { return baseURL() + "/api/transactions/" + intentionId + "/users/" + userId; }
    private String transactionActionsURL(TransactionDTO transaction, Integer userId) {
        return baseURL() + "/api/transactions/" + transaction.getId() + "/users/" + userId;
    }
    private UserRegisterResponseDTO intentionOwner;
    private UserRegisterResponseDTO transactionOwner;
    private IntentionDTO intention;

    @BeforeEach
    public void setUp(){
        intentionOwner = getRegisteredUser(anyUser());
        transactionOwner = getRegisteredUser(anotherUser());

        CryptoQuote cryptoQuote = new CryptoQuote("ALICEUSDT", 220d, LocalDateTime.now());
        Mockito.when(cryptoQuoteService.getCryptoQuote("ALICEUSDT")).thenReturn(cryptoQuote);
        Mockito.when(cryptoQuoteService.getAllCryptoQuotes()).thenReturn(Arrays.asList(cryptoQuote));
        Mockito.when(dollarQuoteService.getDollarQuote()).thenReturn(200d);

        intention = new IntentionDTO("ALICEUSDT", 200, 220d, "sale");
    }

    @Test
    public void whenCreateATransactionTheResponseBodyHasATransactionWithStartedStateStatus() {
        UserDTO userRegisterDTO = anyUser();

        IntentionResponseDTO expressedIntention = expressIntention(intention, intentionOwner).getBody();
        ResponseEntity<TransactionDTO> transaction = createTransactionForIntention(expressedIntention.getId(), transactionOwner.getId());
        TransactionDTO responseBody = transaction.getBody();

        assertEquals(transaction.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getUser().getFirstName(), intentionOwner.getFirstName());
        assertEquals(responseBody.getSymbol(), expressedIntention.getCrypto());
        assertEquals(responseBody.getShippingAddress(), userRegisterDTO.getCvu());
        assertEquals(responseBody.getCryptoPrice(), intention.getCryptoPrice());
        assertEquals(responseBody.getNominalAmount(), intention.getNominalAmount());
        assertNotNull(responseBody.getOperationAmount());
    }

    @Test
    public void whenCreateAWrongTransactionTheResponseIsABadRequest() {
        ResponseEntity<TransactionDTO> transaction = createTransaction(intentionOwner, intentionOwner);

        assertEquals(transaction.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void whenATransactionIsMarkedAsPaidTheResponseIsASuccessfulMessage() {
        TransactionDTO transaction = createTransaction(intentionOwner, transactionOwner).getBody();

        ResponseEntity<String> response = markAs(transaction, "/paid", transactionOwner);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenATransactionIsMarkedAsPaidWithWrongUserTheResponseIsABadRequest() {
        TransactionDTO transaction = createTransaction(intentionOwner, transactionOwner).getBody();

        ResponseEntity<String> response = markAs(transaction, "/paid", intentionOwner);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void whenATransactionMarkedAsPaymentConfirmedTheResponseIsASuccessfulMessage() {
        TransactionDTO transaction = createTransaction(intentionOwner, transactionOwner).getBody();
        markAs(transaction, "/paid", transactionOwner);

        ResponseEntity<String> response = markAs(transaction, "/confirmed", intentionOwner);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenATransactionMarkedAsPaymentConfirmedWithWrongUserTheResponseIsABadRequest() {
        TransactionDTO transaction = createTransaction(intentionOwner, transactionOwner).getBody();
        markAs(transaction, "/paid", transactionOwner);

        ResponseEntity<String> response = markAs(transaction, "/confirmed", transactionOwner);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void whenATransactionMarkedAsCancelTheResponseIsASuccessfulMessage() {
        TransactionDTO transaction = createTransaction(intentionOwner, transactionOwner).getBody();

        ResponseEntity<String> response = markAs(transaction, "/cancel", intentionOwner);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenATransactionCompletedMarkedAsCancelTheResponseIsABadRequest() {
        TransactionDTO transaction = createTransaction(intentionOwner, transactionOwner).getBody();
        markAs(transaction, "/cancel", intentionOwner);

        ResponseEntity<String> response = markAs(transaction, "/cancel", intentionOwner);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void getTradedVolumeReturnsStatusOk() {
        TransactionDTO transaction = createTransaction(intentionOwner, transactionOwner).getBody();
        markAs(transaction, "/paid", transactionOwner);
        markAs(transaction, "/confirmed", intentionOwner);

        ResponseEntity<ReportDTO> response = getTradedVolume(intentionOwner);
        ReportDTO body = response.getBody();

        Double totalInUSD = intention.getNominalAmount() * intention.getCryptoPrice();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(body.getUser(), intentionOwner.getId());
        assertEquals(body.getTotalValueInPesos(), totalInUSD * dollarQuoteService.getDollarQuote());
        assertEquals(body.getTotalValueInUSD(), totalInUSD);
        assertEquals(body.getAssets().size(), 1);
        assertNotNull(body.getDateTime());
    }

    private ResponseEntity<ReportDTO> getTradedVolume(UserRegisterResponseDTO user) {
        return restTemplate.exchange(
                tradedVolumeURL(user),
                HttpMethod.GET,
                new HttpEntity<>(getHttpHeaders()),
                ReportDTO.class
        );
    }

    private String tradedVolumeURL(UserRegisterResponseDTO user) {
        return UriComponentsBuilder.fromHttpUrl(urlUsers() + "/" + user.getId().toString() + "/traded-volume")
                .queryParam("startDate", LocalDate.now().minusDays(2))
                .queryParam("finalDate", LocalDate.now().plusDays(2))
                .build()
                .encode()
                .toUriString();
    }

    private ResponseEntity<String> markAs(TransactionDTO transaction, String actionURL, UserRegisterResponseDTO user) {
        return restTemplate.exchange(
                transactionActionsURL(transaction, user.getId()) + actionURL,
                HttpMethod.PUT,
                new HttpEntity<String>(getHttpHeaders()),
                String.class
        );
    }

    private ResponseEntity<TransactionDTO> createTransaction(UserRegisterResponseDTO intentionOwner, UserRegisterResponseDTO transactionOwner) {
        IntentionResponseDTO expressedIntention = expressIntention(intention, intentionOwner).getBody();
        return createTransactionForIntention(expressedIntention.getId(), transactionOwner.getId());
    }

    private UserRegisterResponseDTO getRegisteredUser(UserDTO userRegisterDTO) {
        return registerUser(userRegisterDTO).getBody();
    }

    private ResponseEntity<TransactionDTO> createTransactionForIntention(Integer intentionId, Integer userId) {
        return restTemplate.postForEntity(
                urlNewTransaction(intentionId, userId),
                new HttpEntity<String>(getHttpHeaders()),
                TransactionDTO.class
        );
    }

}
