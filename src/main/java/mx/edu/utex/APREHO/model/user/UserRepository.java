package mx.edu.utex.APREHO.model.user;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*@Query("SELECT u.*, r.*, p.* FROM User u INNER JOIN Rol r ON u.rol_id = r.rol_id INNER JOIN People p ON u.people_id = p.people_id")
    Optional<User> all(String string);*/

    Optional<User> findByEmail(String string);
    Optional<User> findFirstByEmail(String email);


    @Query(value = "SELECT user_table.*,rol.rol_name,People.curp,People.lastname,People.name FROM user user_table  INNER JOIN rol  on user_table.rol_id=rol.rol_id INNER JOIN People  ON user_table.people_id=People.people_id", nativeQuery = true)
    List<User> getUserss();

    @Query(value = "SELECT user_table.user_id, user_table.password, user_table.email," +
            "       rol.rol_id, rol.rol_name," +
            "       people.people_id, people.name, people.lastname, people.surname, people.sex, people.birthday " +
            "FROM user user_table " +
            "INNER JOIN rol ON user_table.rol_id = rol.rol_id " +
            "INNER JOIN people ON user_table.people_id = people.people_id", nativeQuery = true)
    List<User> getUser();


    @Query(value = "SELECT user_table.people_id,user_table.rol_id,user_table.user_id, user_table.password,user_table.email ," +
            "rol.rol_name," +
            "people.curp,people.lastname,people.name ,people.surname,people.sex,people.birthday  " +
            "FROM user user_table   " +
            "INNER JOIN rol  on user_table.rol_id = rol.rol_id  " +
            "INNER JOIN people  ON user_table.people_id = people.people_id " +
            "WHERE user_table.user_id=:userId", nativeQuery = true)
    Optional<User> findOne(@Param("userId") Long userId);

    @Query(value = "SELECT user_table.people_id,user_table.rol_id,user_table.user_id, user_table.password,user_table.email ," +
            "rol.rol_name," +
            "people.curp,people.lastname,people.name ,people.surname,people.sex,people.birthday  " +
            "FROM user user_table   " +
            "INNER JOIN rol  on user_table.rol_id = rol.rol_id  " +
            "INNER JOIN people  ON user_table.people_id = people.people_id " +
            "WHERE  user_table.password=:password AND user_table.email= :email ", nativeQuery = true)
    Optional<User> loggin( String password, String email);

}
