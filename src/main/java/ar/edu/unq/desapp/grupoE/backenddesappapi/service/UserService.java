package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
