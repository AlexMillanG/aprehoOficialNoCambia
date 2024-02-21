package mx.edu.utex.APREHO.model.roomBean;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {

    Optional<Room> findByHotel_HotelId(Long l);
}
