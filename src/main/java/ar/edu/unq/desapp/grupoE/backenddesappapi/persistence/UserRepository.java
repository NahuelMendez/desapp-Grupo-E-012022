package ar.edu.unq.desapp.grupoE.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoMovement;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    @Query("SELECT movements " +
            "FROM User user " +
            "JOIN user.cryptoMovements movements " +
            "WHERE user.id = ?1 and movements.date BETWEEN ?2 and ?3 ")
    List<CryptoMovement> getCryptoMovementsBetween(Integer id, LocalDate startDate, LocalDate finalDate);

    Optional<User> findByEmail(String email);
}
