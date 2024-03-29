package mx.edu.utex.APREHO.controllers.RoomControllers.Dto;

import lombok.Data;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.room.Room;
import mx.edu.utex.APREHO.model.roomType.RoomType;

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
        return new Room(roomName ,status, peopleQuantity,description,type,hotel);
    }
}
