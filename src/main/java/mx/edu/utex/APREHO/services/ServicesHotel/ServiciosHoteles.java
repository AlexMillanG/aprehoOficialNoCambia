package mx.edu.utex.APREHO.services.ServicesHotel;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotelBean.Hotel;
import mx.edu.utex.APREHO.model.hotelBean.HotelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ServiciosHoteles {
    private final HotelRepository hotelRepository;

  /*  public ResponseEntity<ApiResponse> saveHotel(Hotel hotel){
        Optional<Hotel> foundHotel = hotelRepository.findByEmail(hotel.getEmail());

        if (foundHotel.isPresent()){
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error, ya se ha registrado un hotel con ese emaik"),HttpStatus.BAD_REQUEST);
        }else{

       }
    }*/
}
