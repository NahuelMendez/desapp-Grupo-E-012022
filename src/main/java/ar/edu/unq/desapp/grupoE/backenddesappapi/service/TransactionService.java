package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Transaction;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.TransactionRepository;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IntentionRepository intentionRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction createPurchaseTransaction(Integer intentionId, Integer userId) throws UserException {
        Intention intention = intentionRepository.findById(intentionId).orElseThrow(() -> new UserException("No se encontro la intencion"));
        User buyer = userRepository.findById(userId).orElseThrow(() -> new UserException("No se encontro el usuario"));
        Transaction transaction = new Transaction(LocalDateTime.now(), buyer, intention.getUser(), intention);
        transactionRepository.save(transaction);
        return transaction;
    }

    public Transaction createSaleTransaction(Integer intentionId, Integer userId) throws UserException {
        Intention intention = intentionRepository.findById(intentionId).orElseThrow(() -> new UserException("No se encontro la intencion"));
        User seller = userRepository.findById(userId).orElseThrow(() -> new UserException("No se encontro el usuario"));
        Transaction transaction = new Transaction(LocalDateTime.now(), intention.getUser(), seller, intention);
        transactionRepository.save(transaction);
        return transaction;
    }
}
