package mx.edu.utex.APREHO.model.peopleBean;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People,Long> {

    Optional<People> findByCurp(String string);
}
