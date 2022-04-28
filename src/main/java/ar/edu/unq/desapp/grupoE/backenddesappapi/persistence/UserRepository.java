package ar.edu.unq.desapp.grupoE.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Configuration
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();
}
