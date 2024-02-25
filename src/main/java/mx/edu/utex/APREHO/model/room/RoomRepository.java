package mx.edu.utex.APREHO.model.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {
    @JsonIgnore
    Optional<Room> findByHotel_HotelId(Long l);
}
