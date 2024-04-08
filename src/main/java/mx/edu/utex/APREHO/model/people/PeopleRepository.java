package mx.edu.utex.APREHO.model.people;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PeopleRepository extends JpaRepository<People,Long> {

    Optional<People> findByCurp(String string);
}
