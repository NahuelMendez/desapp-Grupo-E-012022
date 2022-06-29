package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.IntentionRepository;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.UserRepository;
import ar.edu.unq.desapp.grupoE.backenddesappapi.security.JWTProvider;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IntentionRepository intentionRepository;
    @Autowired
    private CryptoQuoteService cryptoQuoteService;
    @Autowired
    private DollarQuoteService dollarQuoteService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public User save(User user) throws UserException {
        Optional<User> findedUser = userRepository.findByEmail(user.getEmail());
        if (findedUser.isPresent())
            throw new UserException("El Email ya existe");
        String password = passwordEncoder.encode(user.getPassword());
        User authUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getAddress(), password, user.getCvu(), user.getWalletAddress());

        return userRepository.save(authUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Intention expressIntention(Integer id, String crypto, Integer nominalAmount, Double cryptoPrice, String operation) throws UserException {
        User user = getUser(id);
        Double dollarExchange = dollarQuoteService.getDollarQuote();
        Intention intention = createIntention(crypto, nominalAmount, cryptoPrice, dollarExchange, operation, user, getCryptoQuote(crypto));
        user.expressIntention(intention);
        Intention intentionResponse = intentionRepository.save(intention);
        userRepository.save(user);
        return intentionResponse;
    }

    private Intention createIntention(String crypto, Integer nominalAmount, Double cryptoPrice, Double dollarExchange, String operation, User user, CryptoQuote quote) throws UserException {
        if (operation.equals("buy")) {
            return new PurchaseIntention(crypto, nominalAmount, cryptoPrice, dollarExchange, user, quote);
        }else if (operation.equals("sale")) {
            return new SaleIntention(crypto, nominalAmount, cryptoPrice, dollarExchange, user, quote);
        }else {
            throw new UserException("No se puede crear la intencion de tipo " + operation + ".La intencion debe ser de tipo buy o sale");
        }
    }

    private CryptoQuote getCryptoQuote(String symbol) {
        return this.cryptoQuoteService.getCryptoQuote(symbol);
    }

    public TradedVolumeReport tradedVolumeOfCryptoAssets(Integer id, LocalDate startDate, LocalDate finalDate) throws UserException {
        List<CryptoMovement> cryptoMovements = userRepository.getCryptoMovementsBetween(id, startDate, finalDate);
        User user = getUser(id);
        return new TradedVolumeReport(user, cryptoMovements, cryptoQuoteService.getAllCryptoQuotes(), dollarQuoteService.getDollarQuote());
    }

    private User getUser(Integer id) throws UserException {
        return userRepository.findById(id).orElseThrow(() -> new UserException("No se encontro el usuario"));
    }

    public User login(User user) {
        Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(), user.getPassword()
                            )
                    );

        return (User) authenticate.getPrincipal();
    }

}
