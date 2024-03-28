package mx.edu.utex.APREHO.model.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    @JsonIgnore
    List<Room> findByHotel_HotelId(Long l);

    List<Room> findByHotel_HotelIdAndAndRoomType_TypeName(Long l, String string);

}
