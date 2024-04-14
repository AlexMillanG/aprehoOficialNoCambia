package mx.edu.utex.APREHO.controllers.RoomTypeControllers.Dto;

import lombok.Data;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.room.Room;
import mx.edu.utex.APREHO.model.roomType.RoomType;

import java.util.Set;

@Data
public class DtoRoomType {
    private Long roomTypeId;
    private String typeName;
    private Set<Room> rooms;
    private Hotel hotel;
    private Double price;
    public RoomType toEntity(){
        return new RoomType(typeName,rooms,hotel,price);
    }
    public RoomType toEntityUpdate(){
        return new RoomType(roomTypeId,typeName,rooms, hotel,price);
    }

}
