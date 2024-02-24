package mx.edu.utex.APREHO.controllers.RoomTypeControllers.Dto;

import lombok.Data;
import mx.edu.utex.APREHO.model.roomBean.Room;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomType;

import java.util.Set;

@Data
public class DtoRoomType {
    private Long roomTypeId;
    private String typeName;
    private Set<Room> rooms;

    public RoomType toEntity(){
        return new RoomType(typeName,rooms);
    }
    public RoomType toEntityUpdate(){
        return new RoomType(roomTypeId,typeName,rooms);
    }

}
