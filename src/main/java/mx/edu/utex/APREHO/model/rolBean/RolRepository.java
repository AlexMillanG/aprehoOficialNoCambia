package mx.edu.utex.APREHO.model.rolBean;

import mx.edu.utex.APREHO.model.userBean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolName(String string);


}
