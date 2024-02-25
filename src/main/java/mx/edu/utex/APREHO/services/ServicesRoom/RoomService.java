package mx.edu.utex.APREHO.services.ServicesRoom;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotelBean.Hotel;
import mx.edu.utex.APREHO.model.hotelBean.HotelRepository;
import mx.edu.utex.APREHO.model.roomBean.Room;
import mx.edu.utex.APREHO.model.roomBean.RoomRepository;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomType;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private  final RoomTypeRepository roomTypeRepository;
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveRoom(Room room){
        if(room.getHotel() == null)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true ,"Error, no se le ha asginado un hotel al cuarto"), HttpStatus.BAD_REQUEST);

        Optional<Hotel> optionalHotel = hotelRepository.findById(room.getHotel().getHotelId());
        if (optionalHotel.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error, el hotel asociado no existe"),HttpStatus.BAD_REQUEST);
        }

        if (room.getPeopleQuantity() <= 0)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error,La cantidad de personas no puede ser menor o igual que cero"),HttpStatus.BAD_REQUEST);

        if (room.getRoomType().getRoomTypeId() == null)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error, debe tener asignado un tipo de cuarto"),HttpStatus.BAD_REQUEST);

            return new ResponseEntity<>(new ApiResponse(roomRepository.saveAndFlush(room),HttpStatus.OK,false,"Cuarto guardado correctamente"),HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> updateRoom(Room room){
        Optional<Room> foundRoom = roomRepository.findById(room.getRoomId());
        if (foundRoom == null)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true ,"No se encontró el hotel a actualizar"), HttpStatus.BAD_REQUEST);
        if(room.getHotel() == null)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true ,"Error, no se le ha asginado un hotel al cuarto"), HttpStatus.BAD_REQUEST);
        if (room.getPeopleQuantity() <= 0)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error,La cantidad de personas no puede ser menor o igual que cero"),HttpStatus.BAD_REQUEST);
        if (room.getRoomType().getTypeName() == null) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error, debe tener asignado un tipo de cuarto"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(roomRepository.saveAndFlush(room),HttpStatus.OK,false,"Cuarto guardado correctamente"),HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse> deleteRoom(Long id) {
        Optional<Room> foundRoom = roomRepository.findById(id);

        if (foundRoom.isPresent()){
            roomRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Habitación eliminada correctamente"), HttpStatus.OK);}
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "Error, no se encontró el cuarto a eliminar"), HttpStatus.NOT_FOUND);


    }
    public ResponseEntity<ApiResponse> getByHotel(Long id){
        Optional<Room> foundHotelsRooms = roomRepository.findByHotel_HotelId(id);
        if (foundHotelsRooms == null)
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"Error, no se encontró el hotel asociado"),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse(foundHotelsRooms.get(),HttpStatus.OK),HttpStatus.OK);

    }
    //el getAll no se va a quedar es solo para pruebas
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(roomRepository.findAll(), HttpStatus.OK),HttpStatus.OK);
    }

}