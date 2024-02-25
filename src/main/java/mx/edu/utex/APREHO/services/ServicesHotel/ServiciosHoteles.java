package mx.edu.utex.APREHO.services.ServicesHotel;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.hotel.HotelRepository;
import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.model.user.UserRepository;
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
            //checa que el hotel sea único
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, ya se ha registrado un hotel con ese email"), HttpStatus.BAD_REQUEST);
        } else {
            //si es unico checa que este asociado a un usuario
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
                for (User user : hotel.getUser()) {
                    System.err.println(user.getUserId());
                }
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Guardado correctamente"), HttpStatus.OK);
            }
        }
    }
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> getAll() {
        return new ResponseEntity<>(new ApiResponse(hotelRepository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    //muestra los hoteles por ciudad
    public ResponseEntity<ApiResponse>getByCity(String city){
        Optional<Hotel> foundCity = hotelRepository.findByCity(city);
        if (!foundCity.isPresent())
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"Error, ciudad no encontrada"),HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(new ApiResponse(foundCity.get(),HttpStatus.OK,false,"ciudad encontrada"),HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> updateHotel(Hotel hotel) {
        Optional<Hotel> foundHotel = hotelRepository.findByEmail(hotel.getEmail());
        if (!foundHotel.isPresent()) {
            //checa que el hotel sea único
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, hotel no encontrado"), HttpStatus.BAD_REQUEST);
        } else {
            //si es unico checa que este asociado a un usuario
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
                for (User user : hotel.getUser()) {
                    System.err.println(user.getUserId());
                }
                return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Actualizado correctamente"), HttpStatus.OK);
            }
        }
    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse>deleteHotel(String email){
        Optional<Hotel> foundHotel = hotelRepository.findByEmail(email);
        if (foundHotel.isEmpty())
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"No se encontró el hotel"),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse(hotelRepository.deleteByEmail(email),HttpStatus.OK,false,"Hotel eliminado correctamente"),HttpStatus.OK);
    }
}
