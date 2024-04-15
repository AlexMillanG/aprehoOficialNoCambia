package mx.edu.utex.APREHO.controllers.UserControllers.Dto;

import lombok.Data;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.people.People;
import mx.edu.utex.APREHO.model.rol.Rol;
import mx.edu.utex.APREHO.model.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Data
public class DtoUser {

    private Long userId;
    private String password;
    private String email;

    private Rol rol;
    private People people;
    private Set<Hotel> hotel;


    public User toEntity() {
        return new User( password,email, rol, people);
    }

    public User toEntityId() {
        return new User(userId,password,email, rol, people);
    }

    public  User toReceptionst(){
        return  new User(password,email,rol,people,hotel);
    }
}
