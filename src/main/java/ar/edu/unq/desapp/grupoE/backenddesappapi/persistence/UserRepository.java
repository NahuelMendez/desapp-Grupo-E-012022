package ar.edu.unq.desapp.grupoE.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    @Query("SELECT SUM(intention.nominalAmount) " +
            "FROM Transaction transaction, CompleteTransactionState state " +
            "JOIN transaction.intention intention " +
            "JOIN intention.user user " +
            "WHERE user.id = ?1 and transaction.date BETWEEN ?2 and ?3")
    Integer getTradedVolumeOfCryptoAssets(Integer id, LocalDateTime startDate, LocalDateTime finalDate);
}
