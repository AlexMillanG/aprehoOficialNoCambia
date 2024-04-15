package mx.edu.utex.APREHO.controllers.ReservationsControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.ReservationsControllers.Dto.DtoReservations;
import mx.edu.utex.APREHO.services.ServicesReservations.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin({"*"})
@AllArgsConstructor
public class ReservationsControllers {
    private final ReservationService service;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveReservation(@RequestBody DtoReservations dtoReservations){
        return service.saveReservations(dtoReservations.toEntity());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteReservation(@PathVariable Long id){
        return service.deleteReservation(id);
    }


    @GetMapping("/getByPerson/{id}")
    public  ResponseEntity<ApiResponse> findByPerson(@PathVariable  Long id){
        return  service.findByPerson(id);
    }


}
