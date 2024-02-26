package mx.edu.utex.APREHO.services.ServicesRoom;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.hotel.HotelRepository;
import mx.edu.utex.APREHO.model.room.Room;
import mx.edu.utex.APREHO.model.room.RoomRepository;
import mx.edu.utex.APREHO.model.roomType.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
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

    @Transactional(rollbackFor = {SQLException.class})

    public ResponseEntity<ApiResponse> deleteRoom(Long id) {
        Optional<Room> foundRoom = roomRepository.findById(id);

        if (foundRoom.isPresent()){
            roomRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Habitación eliminada correctamente"), HttpStatus.OK);}
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "Error, no se encontró el cuarto a eliminar"), HttpStatus.NOT_FOUND);


    }
    @Transactional(rollbackFor = {SQLException.class})

    public ResponseEntity<ApiResponse> getByHotel(Long id) {
        List<Room> hotelRooms = roomRepository.findByHotel_HotelId(id);
        if (!hotelRooms.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(hotelRooms, HttpStatus.OK), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "Error, no se encontraron habitaciones asociadas"), HttpStatus.NOT_FOUND);
        }
    }

    //el getAll no se va a quedar es solo para pruebas
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(roomRepository.findAll(), HttpStatus.OK),HttpStatus.OK);
    }

    @Transactional (rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> findOneRoom(Long id){
        Optional<Room> foundOneRoom = roomRepository.findById(id);
        if (foundOneRoom.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"No se encontró la habitación"),HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(new ApiResponse(foundOneRoom.get(),HttpStatus.OK),HttpStatus.OK);
    }
}
