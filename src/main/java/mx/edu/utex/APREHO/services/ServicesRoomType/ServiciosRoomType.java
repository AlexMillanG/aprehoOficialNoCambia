package mx.edu.utex.APREHO.services.ServicesRoomType;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomType;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ServiciosRoomType {
    private RoomTypeRepository roomTypeRepository;

    public ResponseEntity<ApiResponse> saveRoomType(RoomType roomType){
            return new ResponseEntity<>(new ApiResponse(roomTypeRepository.saveAndFlush(roomType),HttpStatus.BAD_REQUEST,true ,"Error, precio no valido"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ApiResponse> updateRoomType(RoomType roomType){
            return new ResponseEntity<>(new ApiResponse(roomTypeRepository.saveAndFlush(roomType),HttpStatus.BAD_REQUEST,true ,"Error, precio no valido"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ApiResponse> deleteRoomType(Long id){
        Optional<RoomType> foundRoomType = roomTypeRepository.findById(id);
        if (!foundRoomType.isPresent())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true ,"Error, no se encontró el tipo de cuarto a eliminar"), HttpStatus.BAD_REQUEST);
            roomTypeRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,false ,"Tipo de cuarto eliminado con exito"), HttpStatus.BAD_REQUEST);
    }

    //select type_name from room inner join room_type on room_type.room_type_id =room.room_type_id inner join hotel on hotel.hotel_id =room.hotel_id;
}
