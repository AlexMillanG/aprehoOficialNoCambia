package mx.edu.utex.APREHO.model.roomTypeBean;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomTypeRepository  extends JpaRepository<RoomType,Long> {

    Optional<RoomType> findByTypeName(String s);
}
