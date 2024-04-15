package mx.edu.utex.APREHO.services.auth;

import lombok.*;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.people.People;
import mx.edu.utex.APREHO.model.rol.Rol;
import mx.edu.utex.APREHO.model.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoAuth {

    private String token;
    private People people;
    private User user;
    private Rol rol;
    private Hotel hotel;


}
