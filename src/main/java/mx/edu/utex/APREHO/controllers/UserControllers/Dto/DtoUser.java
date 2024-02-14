package mx.edu.utex.APREHO.controllers.UserControllers.Dto;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utex.APREHO.model.hotelBean.Hotel;
import mx.edu.utex.APREHO.model.peopleBean.People;
import mx.edu.utex.APREHO.model.rolBean.Rol;
import mx.edu.utex.APREHO.model.userBean.User;

import java.util.Set;

@Data
public class DtoUser {

    private Long userId;
    private String username;
    private String password;
    private Rol rol;
    private People people;
    private Set<Hotel> hotel;

    public DtoUser(String username, String password, People people, Rol rol) {
    }


    public User toEntity() {
        return new User(username, password, rol, people);
    }
}
