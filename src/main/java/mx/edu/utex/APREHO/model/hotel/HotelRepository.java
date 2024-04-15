package mx.edu.utex.APREHO.model.hotel;

import mx.edu.utex.APREHO.model.images.Images;
import mx.edu.utex.APREHO.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

    Optional<Hotel> findByEmail(String email);
    List<Hotel> findByCity(String city);
    Optional<Hotel> deleteByEmail(String email);

    List<Hotel> findByUser(User user);
    //List<Hotel> findAllByCity();






}
