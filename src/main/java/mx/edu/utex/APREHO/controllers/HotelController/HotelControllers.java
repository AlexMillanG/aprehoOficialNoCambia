package mx.edu.utex.APREHO.controllers.HotelController;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.HotelController.Dto.DtoHotel;
import mx.edu.utex.APREHO.services.ServicesHotel.ServiciosHoteles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin({"*"})
@AllArgsConstructor
public class HotelControllers {
    private final ServiciosHoteles service;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse>save(@RequestBody DtoHotel hotel){
        System.err.println(hotel.toEntity().toString());
        return  service.saveHotel(hotel.toEntity());
    }
}
