package mx.edu.utex.APREHO.services.ServicesRoomType;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotel.HotelRepository;
import mx.edu.utex.APREHO.model.roomType.RoomType;
import mx.edu.utex.APREHO.model.roomType.RoomTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class RoomTypeService {
    private RoomTypeRepository roomTypeRepository;
    private HotelRepository hotelRepository;

    public ResponseEntity<ApiResponse> saveRoomType(RoomType roomType){
            return new ResponseEntity<>(new ApiResponse(roomTypeRepository.saveAndFlush(roomType),HttpStatus.OK,false ,"tipo de cuarto guardado con exito"), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> updateRoomType(RoomType roomType){
        Optional<RoomType> existingRoomType = roomTypeRepository.findById(roomType.getRoomTypeId());

        if (!existingRoomType.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, el tipo de cuarto que se desea actualizar no existe"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse(roomTypeRepository.saveAndFlush(roomType),HttpStatus.OK,false ,"tipo de cuarto guardado con exito"), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> deleteRoomType(Long id){
      Optional<RoomType> foundRoomType = roomTypeRepository.findById(id);
        if (!foundRoomType.isPresent())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true ,"Error, no se encontró el tipo de cuarto a eliminar"), HttpStatus.BAD_REQUEST);

        RoomType roomType = foundRoomType.get();

        if (!roomType.getRooms().isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, el tipo de cuarto está asociado a una o más habitaciones"), HttpStatus.BAD_REQUEST);
        }

            roomTypeRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,false ,"Tipo de cuarto eliminado con exito"), HttpStatus.OK);
    }
    public ResponseEntity<ApiResponse> getRoomTypeByHotel(Long id){
        System.err.println("antes found" + id);
        Optional<RoomType> foundTypeByHotel = roomTypeRepository.findByHotel_HotelId(id);
        System.err.println("despues found"+foundTypeByHotel);
        if (!foundTypeByHotel.isPresent())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true ,"Error, no hay tipos de cuartos asociados a este hotel"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(foundTypeByHotel.get(),HttpStatus.OK),HttpStatus.OK);

    }

    //el getAll no se va a quedar es solo para pruebas
    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(roomTypeRepository.findAll(), HttpStatus.OK),HttpStatus.OK);
    }



}
