package mx.edu.utex.APREHO.controllers.HotelController;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.HotelController.Dto.DtoHotel;
import mx.edu.utex.APREHO.services.ServicesHotel.HotelsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin({"*"})
@AllArgsConstructor
public class HotelControllers {
    private final HotelsService service;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse>save(@RequestBody DtoHotel hotel){
        System.out.println(hotel.getCity());
        String city = hotel.getCity();
        // Verificar si la ciudad es nula o está vacía
        if (city == null || city.isEmpty()) {
            return  new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "La ciudad no puede estar vacía"),HttpStatus.BAD_REQUEST);
        }
        // Verificar si la ciudad tiene espacios al principio o al final
        if (!city.equals(city.trim())) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "La ciudad no puede tener espacios al principio ni al final"),HttpStatus.BAD_REQUEST);
        }
        return  service.saveHotel(hotel.toEntity());
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){
        return service.getAll();
    }
    @GetMapping("/{city}")
    public ResponseEntity<ApiResponse>getByCity(@PathVariable String city){
        return service.getByCity(city);
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse>update(@RequestBody DtoHotel hotel){
        return  service.updateHotel(hotel.toEntityUpdate());
    }
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<ApiResponse>delete(@PathVariable String email){
        return service.deleteHotel(email);
    }

    @GetMapping("/findOne/{id}")
    public ResponseEntity<ApiResponse> findOne(@PathVariable Long id) {
        return service.findOneHotel(id);
    }



}
