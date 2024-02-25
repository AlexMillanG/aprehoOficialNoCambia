package mx.edu.utex.APREHO.model.roomTypeBean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoomTypeRepository  extends JpaRepository<RoomType,Long> {

    Optional<RoomType> findByTypeName(String s);


    @Query(value = "SELECT type_name \n" +
            "            FROM hotel \n" +
            "            LEFT JOIN room ON hotel.hotel_id = room.hotel_id \n" +
            "            INNER JOIN room_type ON room.room_type_id = room_type.room_type_id \n" +
            "            WHERE hotel.hotel_id = :id;", nativeQuery = true)
    Optional<RoomType> findByHotelId(@Param("id") Long id);
}
