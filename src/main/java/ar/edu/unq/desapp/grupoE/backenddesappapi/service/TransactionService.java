package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
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

    @Autowired
    private CryptoQuoteService cryptoQuoteService;

    public Transaction createPurchaseTransaction(Integer intentionId, Integer userId) throws UserException {
        Intention intention = findIntention(intentionId);
        User buyer = findUser(userId);
        Transaction transaction = new Transaction(LocalDateTime.now(), buyer, intention.getUser(), intention);
        transactionRepository.save(transaction);
        return transaction;
    }

    public Transaction createSaleTransaction(Integer intentionId, Integer userId) throws UserException {
        Intention intention = findIntention(intentionId);
        User seller = findUser(userId);
        Transaction transaction = new Transaction(LocalDateTime.now(), intention.getUser(), seller, intention);
        transactionRepository.save(transaction);
        return transaction;
    }

    public void markAsPaid(Integer transactionId, Integer userId) throws UserException {
        Transaction transaction = findTransaction(transactionId);
        User user = findUser(userId);

        transaction.doTransfer(user);

        transactionRepository.save(transaction);
    }

    public void cancel(Integer transactionId, Integer userId) throws UserException {
        Transaction transaction = findTransaction(transactionId);
        User user = findUser(userId);

        transaction.cancelOperation(user);

        transactionRepository.save(transaction);
    }

    public void markAsPaymentConfirmed(Integer transactionId, Integer userId) throws UserException {
        Transaction transaction = findTransaction(transactionId);
        User user = findUser(userId);
        CryptoQuote cryptoQuote = cryptoQuoteService.getCryptoQuote(transaction.getIntention().getActiveCrypto());

        transaction.confirmTransferFor(user, LocalDateTime.now(), cryptoQuote );

        transactionRepository.save(transaction);
    }

    private User findUser(Integer userId) throws UserException {
        return userRepository.findById(userId).orElseThrow(() -> new UserException("No se encontro el usuario"));
    }

    private Intention findIntention(Integer intentionId) throws UserException {
        return intentionRepository.findById(intentionId).orElseThrow(() -> new UserException("No se encontro la intencion"));
    }

    private Transaction findTransaction(Integer transactionId) throws UserException {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new UserException("No se encontro la transaccion"));
    }
}
