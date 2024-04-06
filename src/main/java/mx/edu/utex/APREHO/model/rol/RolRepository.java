package mx.edu.utex.APREHO.model.rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolName(String string);


}
