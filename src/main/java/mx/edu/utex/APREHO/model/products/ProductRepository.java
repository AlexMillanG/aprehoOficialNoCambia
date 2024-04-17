package mx.edu.utex.APREHO.model.products;

import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    Optional<Products> findByProductName(String productName);


    List<Products> findByHotel(Hotel hotel);
}
