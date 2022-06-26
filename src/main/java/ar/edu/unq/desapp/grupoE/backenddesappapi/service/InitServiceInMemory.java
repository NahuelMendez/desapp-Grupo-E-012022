package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import javax.annotation.PostConstruct;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InitServiceInMemory {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize() throws UserException {
        if (className.equals("org.h2.Driver")) {
            logger.info("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() throws UserException {
        User user = new User("Pepe", "Pepa", "email@gmail.com", "San Martin 185", "unaPassw123??", "1234567891234567891234", "12345678");
        User user2 = new User("Samanta", "Quiroga", "email2@gmail.com", "San Martin 185", "unaPassw123??", "1234567891234567891234", "12345678");
        userService.save(user);
        userService.save(user2);
    }
}
