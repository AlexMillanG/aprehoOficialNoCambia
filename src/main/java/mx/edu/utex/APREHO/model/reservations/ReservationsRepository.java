package mx.edu.utex.APREHO.model.reservations;

import mx.edu.utex.APREHO.model.people.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ReservationsRepository extends JpaRepository<ReservationsBean,Long> {
    Optional<ReservationsBean> deleteByReservationId(Long id);
    List<ReservationsBean> findByRoom_RoomId(Long id);
    List<ReservationsBean> findByPeople(People people);
}
