package mx.edu.utex.APREHO.model.userBean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    /*@Query("SELECT u.*, r.*, p.* FROM User u INNER JOIN Rol r ON u.rol_id = r.rol_id INNER JOIN People p ON u.people_id = p.people_id")
    Optional<User> all(String string);*/

 /*   @Query("SELECT u.*, r.*, p.* FROM User u INNER JOIN Rol r ON u.rol_id = r.rol_id INNER JOIN People p ON u.people_id = p.people_id")
    Optional<User> all(String string);*/



}
