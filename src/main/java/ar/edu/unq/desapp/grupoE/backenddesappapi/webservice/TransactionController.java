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
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@Validated
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/api/transaction/{intentionId}/user/{userId}/purchase")
    public ResponseEntity<TransactionDTO> createPurchaseTransaction(
            @PathVariable("intentionId") Integer intentionId,
            @PathVariable("userId") Integer userId) throws UserException {

        Transaction transaction = transactionService.createPurchaseTransaction(intentionId, userId);

        return ResponseEntity.ok().body(new TransactionDTO(transaction));
    };

    @PostMapping(value = "/api/transaction/{intentionId}/user/{userId}/sale")
    public ResponseEntity<TransactionDTO> createSaleTransaction(
            @PathVariable("intentionId") Integer intentionId,
            @PathVariable("userId") Integer userId) throws UserException {

        Transaction transaction = transactionService.createSaleTransaction(intentionId, userId);

        return ResponseEntity.ok().body(new TransactionDTO(transaction));
    };


}
