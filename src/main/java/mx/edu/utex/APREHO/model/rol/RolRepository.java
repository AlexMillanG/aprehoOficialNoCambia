package mx.edu.utex.APREHO.model.rol;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolName(String string);

    Optional<Rol> findByRolId(Long id);

}
