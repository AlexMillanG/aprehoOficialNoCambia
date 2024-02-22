package mx.edu.utex.APREHO.controllers.RoomControllers.Dto;

import lombok.Data;
import mx.edu.utex.APREHO.model.hotelBean.Hotel;
import mx.edu.utex.APREHO.model.roomBean.Room;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomType;

@Data
public class DtoRoom {

    private Long roomId;
    private String roomName;
    private String status;
    private int peopleQuantity;
    private String description;
    private Hotel hotel;
    private RoomType type;

    public Room toEntityUpdate(){
    return new Room(roomId,roomName ,status, peopleQuantity,description,type,hotel );
    }

    public Room toEntity(){
        return new Room(roomName ,status, peopleQuantity,description,hotel );
    }
}
