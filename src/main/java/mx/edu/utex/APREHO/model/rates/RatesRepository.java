package mx.edu.utex.APREHO.model.rates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatesRepository extends JpaRepository<Rates,Long> {

    Optional<Rates> findBySeason(String s);
}
