package mx.edu.utex.APREHO.model.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    Optional<Products> findByProductName(String productName);


}
