package mx.edu.utex.APREHO.services.ServicesRoom;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotelBean.HotelRepository;
import mx.edu.utex.APREHO.model.roomBean.Room;
import mx.edu.utex.APREHO.model.roomBean.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
@Transactional
@Service
public class ServiciosRoom {
    private final RoomRepository roomRepository;
    private final HotelRepository repository;
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveRoom(Room room){
        if(room.getHotel() == null)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true ,"Error, no se le ha asginado un hotel al cuarto"), HttpStatus.BAD_REQUEST);
        if (room.getPeopleQuantity() <= 0)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error,La cantidad de personas no puede ser menor o igual que cero"),HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(new ApiResponse(roomRepository.saveAndFlush(room),HttpStatus.OK,false,"Cuarto guardado correctamente"),HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> UpdateRoom(Room room){
        Optional<Room> foundHotel = roomRepository.findById(room.getRoomId());
        if (!foundHotel.isPresent())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"Error, no se encontró el cuarto a actualizar"),HttpStatus.NOT_FOUND);
        if(room.getHotel() == null)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true ,"Error, no se le ha asginado un hotel al cuarto"), HttpStatus.BAD_REQUEST);
        if (room.getPeopleQuantity() <= 0)
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error,La cantidad de personas no puede ser menor o igual que cero"),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(roomRepository.saveAndFlush(room),HttpStatus.OK,false,"Cuarto guardado correctamente"),HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse> deleteRoom(Long id){
        Optional<Room> foundHotel = roomRepository.findById(id);
        if (foundHotel != null)
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"Error, no se encontró el cuarto a eliminar"),HttpStatus.NOT_FOUND);
        roomRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,false,"Habitación eliminada correctamente"),HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> getByHotel(Long id){
        Optional<Room> foundHotelsRooms = roomRepository.findByHotel_HotelId(id);
        if (foundHotelsRooms == null)
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"Error, no se encontró el hotel asociado"),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse(foundHotelsRooms.get(),HttpStatus.OK),HttpStatus.OK);

    }
}
