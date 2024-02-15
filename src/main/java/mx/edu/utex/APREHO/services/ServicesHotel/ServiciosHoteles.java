package mx.edu.utex.APREHO.services.ServicesHotel;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotelBean.Hotel;
import mx.edu.utex.APREHO.model.hotelBean.HotelRepository;
import mx.edu.utex.APREHO.model.userBean.User;
import mx.edu.utex.APREHO.model.userBean.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ServiciosHoteles {
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveHotel(Hotel hotel) {
        Optional<Hotel> foundHotel = hotelRepository.findByEmail(hotel.getEmail());
        if (foundHotel.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, ya se ha registrado un hotel con ese email"), HttpStatus.BAD_REQUEST);
        } else {
            if (hotel.getUser() == null) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, no se ha insertado un dueño del hotel"), HttpStatus.BAD_REQUEST);
            } else {
                for (User user : hotel.getUser()) {
                    Optional<User> foundUser = userRepository.findById(user.getUserId());
                    if (!foundUser.isPresent()) {
                        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, no se ha encontrado el usuario asociado"), HttpStatus.BAD_REQUEST);
                    }
                }
                hotelRepository.saveAndFlush(hotel);
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Guardado correctamente"), HttpStatus.OK);
            }
        }
    }

  /*  public ResponseEntity<ApiResponse> getAll() {
        return new ResponseEntity<>(new ApiResponse(hotelRepository.saveAndFlush(h),HttpStatus.OK, false, "Usuarios registrados"), HttpStatus.OK);
    }*/
}
