package mx.edu.utex.APREHO.controllers.RoomControllers.Dto;

import lombok.Data;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.images.Images;
import mx.edu.utex.APREHO.model.room.Room;
import mx.edu.utex.APREHO.model.roomType.RoomType;

import java.util.Set;

@Data
public class DtoRoom {

    private Long roomId;
    private String roomName;
    private String status;
    private int peopleQuantity;
    private String description;
    private Hotel hotel;
    private RoomType type;
    private Set<Images> images;

    public Room toEntityUpdate(){
    return new Room(roomId,roomName ,status, peopleQuantity,description,type,hotel );
    }

    public Room toEntity(){
        return new Room(roomName ,status, peopleQuantity,description,type,hotel);
    }


    public DtoRoom(Long roomId, String roomName, String status, int peopleQuantity, String description, RoomType type, Set<Images> images) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.status = status;
        this.peopleQuantity = peopleQuantity;
        this.description = description;
        this.type = type;
        this.images = images;
    }
}
