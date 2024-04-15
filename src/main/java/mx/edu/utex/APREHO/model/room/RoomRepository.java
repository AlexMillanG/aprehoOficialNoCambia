package mx.edu.utex.APREHO.model.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    @JsonIgnore
    List<Room> findByHotel_HotelId(Long l);

    List<Room> findByHotel_HotelIdAndAndRoomType_TypeName(Long l, String string);

    int countAllByHotel(Hotel hotel);


    @Query("SELECT COUNT(r.roomId) FROM Room r JOIN r.hotel h JOIN h.user u WHERE u.userId = :userId")
    int countRoomsByUserId(@Param("userId") Long userId);
}
