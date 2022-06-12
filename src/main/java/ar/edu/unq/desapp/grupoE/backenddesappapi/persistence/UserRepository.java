package ar.edu.unq.desapp.grupoE.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    @Query("SELECT SUM(intention.nominalAmount) " +
            "FROM Transaction transaction, CompleteTransactionState state " +
            "JOIN transaction.intention intention " +
            "JOIN intention.user user " +
            "WHERE user.id = ?1")
    Integer readId(Integer id);
}
