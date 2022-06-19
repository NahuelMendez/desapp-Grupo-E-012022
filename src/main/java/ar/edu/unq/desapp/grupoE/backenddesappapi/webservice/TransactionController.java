package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Transaction;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.TransactionService;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@Validated
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/api/transactions/{intentionId}/users/{userId}")
    public ResponseEntity<TransactionDTO> createTransaction(
            @PathVariable("intentionId") Integer intentionId,
            @PathVariable("userId") Integer userId) throws UserException {

        Transaction transaction = transactionService.createTransaction(intentionId, userId);

        return ResponseEntity.ok().body(new TransactionDTO(transaction));
    }

    @PutMapping(value = "/api/transactions/{transactionId}/users/{userId}/paid")
    public ResponseEntity<String> markAsPaid(
            @PathVariable("transactionId") Integer transactionId,
            @PathVariable("userId") Integer userId) throws UserException {
        transactionService.markAsPaid(transactionId, userId);
        return ResponseEntity.ok().body("successful action");
    }

    @PutMapping(value = "/api/transactions/{transactionId}/users/{userId}/cancel")
    public ResponseEntity<String> cancel(
            @PathVariable("transactionId") Integer transactionId,
            @PathVariable("userId") Integer userId) throws UserException {
        transactionService.cancel(transactionId, userId);
        return ResponseEntity.ok().body("successful action");
    }

    @PutMapping(value = "/api/transactions/{transactionId}/users/{userId}/confirmed")
    public ResponseEntity<String> markAsPaymentConfirmed(
            @PathVariable("transactionId") Integer transactionId,
            @PathVariable("userId") Integer userId) throws UserException {
        transactionService.markAsPaymentConfirmed(transactionId, userId);
        return ResponseEntity.ok().body("successful action");
    }
}
