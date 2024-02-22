package mx.edu.utex.APREHO.model.userBean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    /*@Query("SELECT u.*, r.*, p.* FROM User u INNER JOIN Rol r ON u.rol_id = r.rol_id INNER JOIN People p ON u.people_id = p.people_id")
    Optional<User> all(String string);*/

   @Query(value = "SELECT user_table.*,rol.rol_name,People.curp,People.lastname,People.name FROM user user_table  INNER JOIN rol  on user_table.rol_id=rol.rol_id INNER JOIN People  ON user_table.people_id=People.people_id",nativeQuery = true)
    List<User> getUserss();
   @Query(value = "SELECT user_table.people_id,user_table.rol_id,user_table.user_id, user_table.password,user_table.username ," +
           "rol.rol_name," +
           "people.curp,people.lastname,people.name " +
           "FROM user user_table  " +
           "INNER JOIN rol  on user_table.rol_id = rol.rol_id " +
           "INNER JOIN people  ON user_table.people_id = people.people_id",nativeQuery = true)
   List<User> getUser();



}
