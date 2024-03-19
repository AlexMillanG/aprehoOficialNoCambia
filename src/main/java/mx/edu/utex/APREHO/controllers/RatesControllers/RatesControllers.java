/*package mx.edu.utex.APREHO.controllers.RatesControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.RatesControllers.Dto.DtoRates;
import mx.edu.utex.APREHO.services.ServicesRates.ServiciosRates;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rates")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class RatesControllers {
    private final ServiciosRates rates;

    @GetMapping("getAll")
    public ResponseEntity<ApiResponse> getAll(){
        return rates.getAll();
    }

    @PostMapping("save")
    public  ResponseEntity<ApiResponse> save(@RequestBody DtoRates ratesDto){
        return rates.saveRate(ratesDto.toEntity());
    }
}
*/