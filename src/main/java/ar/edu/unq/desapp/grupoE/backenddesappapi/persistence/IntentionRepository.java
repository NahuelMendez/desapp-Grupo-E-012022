package ar.edu.unq.desapp.grupoE.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Configuration
@Repository
public interface IntentionRepository extends CrudRepository<Intention, Integer> {

    List<Intention> findAll();

}
