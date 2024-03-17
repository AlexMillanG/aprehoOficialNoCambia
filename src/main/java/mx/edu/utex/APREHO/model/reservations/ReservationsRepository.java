package mx.edu.utex.APREHO.model.reservations;

import mx.edu.utex.APREHO.model.people.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationsRepository extends JpaRepository<ReservationsBean,Long> {

}
