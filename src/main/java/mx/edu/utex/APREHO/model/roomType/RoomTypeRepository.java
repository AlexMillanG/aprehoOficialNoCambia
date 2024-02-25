package mx.edu.utex.APREHO.model.roomType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoomTypeRepository  extends JpaRepository<RoomType,Long> {

    Optional<RoomType> findByTypeName(String s);

    /*
    @Query(value = "SELECT type_name FROM room_type LEFT JOIN room ON hotel.hotel_id = room.hotel_id INNER JOIN room_type ON room.room_type_id = room_type.room_type_id WHERE hotel.hotel_id = :id;", nativeQuery = true)
    Optional<RoomType> findByHotelId(@Param("id") Long id);*/

    Optional<RoomType> findByHotel_HotelId(Long id);

}
