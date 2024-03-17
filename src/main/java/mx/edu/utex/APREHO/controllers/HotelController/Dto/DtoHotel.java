package mx.edu.utex.APREHO.controllers.HotelController.Dto;

import lombok.Data;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.user.User;

import java.util.Set;

@Data
public class DtoHotel {
    private Long hotelId;
    private String hotelName;
    private String address;
    private String email;
    private String phone;
    private String city;
    private Set <User> user;
    private String description;

    public Hotel toEntity(){
        return new Hotel(hotelId,hotelName,address,email,phone,city,user, description);
    }
    public Hotel toEntityUpdate(){
        return new Hotel(hotelId,hotelName,address,email,phone,city,description);
    }

}
