package mx.edu.utex.APREHO.model.products;

import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    Optional<Products> findByProductName(String productName);
    List<Products> findByHotel(Hotel hotel);



    @Modifying
    @Query(value = "INSERT INTO hotelproducts (product_id, hotel_id) VALUES (:id_producto, :id_hotel)", nativeQuery = true)
    void saveHotelProducts(@Param("id_producto") Long id_producto, @Param("id_hotel") Long id_hotel);


}
