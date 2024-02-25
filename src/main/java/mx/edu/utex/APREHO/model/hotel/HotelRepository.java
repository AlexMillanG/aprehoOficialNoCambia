package mx.edu.utex.APREHO.model.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    Optional<Hotel> findByEmail(String email);
    Optional<Hotel> findByCity(String city);
    Optional<Hotel> deleteByEmail(String email);


}
