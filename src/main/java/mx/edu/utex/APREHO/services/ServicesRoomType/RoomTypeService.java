package mx.edu.utex.APREHO.services.ServicesRoomType;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotelBean.HotelRepository;
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
public class RoomTypeService {
    private RoomTypeRepository roomTypeRepository;
    private HotelRepository hotelRepository;

    public ResponseEntity<ApiResponse> saveRoomType(RoomType roomType){
            return new ResponseEntity<>(new ApiResponse(roomTypeRepository.saveAndFlush(roomType),HttpStatus.OK,false ,"tipo de cuarto guardado con exito"), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> updateRoomType(RoomType roomType){
            return new ResponseEntity<>(new ApiResponse(roomTypeRepository.saveAndFlush(roomType),HttpStatus.BAD_REQUEST,true ,"Error, precio no valido"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ApiResponse> deleteRoomType(Long id){
      Optional<RoomType> foundRoomType = roomTypeRepository.findById(id);
        if (!foundRoomType.isPresent())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true ,"Error, no se encontró el tipo de cuarto a eliminar"), HttpStatus.BAD_REQUEST);
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
}
