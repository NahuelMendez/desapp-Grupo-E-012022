package ar.edu.unq.desapp.grupoE.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.CryptoQuote;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Configuration
@Repository
public interface CryptoRepository extends CrudRepository<CryptoQuote, Integer> {

    @Query( "SELECT quote " +
            "FROM CryptoQuote quote " +
            "WHERE quote.id IN " + "(SELECT MAX(quote.id) " +
                                    "FROM CryptoQuote quote " +
                                    "GROUP BY quote.name)")
    List<CryptoQuote> findCryptoQuoteLastUpdate();
}
