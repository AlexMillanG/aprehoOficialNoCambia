package mx.edu.utex.APREHO.controllers.RoomControllers.Dto;

import lombok.Data;
import mx.edu.utex.APREHO.model.hotelBean.Hotel;
import mx.edu.utex.APREHO.model.roomBean.Room;

@Data
public class DtoRoom {

    private Long roomId;

    private String status;

    private int peopleQuantity;

    private String description;
    private Hotel hotel;
    public Room toEntity(){
        return new Room();
    }
}
