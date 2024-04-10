package mx.edu.utex.APREHO.model.roomType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoomTypeRepository  extends JpaRepository<RoomType,Long> {

    Optional<RoomType> findByTypeName(String s);


    Optional<RoomType> findByHotel_HotelId(Long id);

}
