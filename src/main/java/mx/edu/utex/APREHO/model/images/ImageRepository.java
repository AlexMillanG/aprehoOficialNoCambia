package mx.edu.utex.APREHO.model.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long>{
}
