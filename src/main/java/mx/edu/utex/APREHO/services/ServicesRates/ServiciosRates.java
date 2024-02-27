package mx.edu.utex.APREHO.services.ServicesRates;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.rates.Rates;
import mx.edu.utex.APREHO.model.rates.RatesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ServiciosRates {
    private final RatesRepository repository;

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveRate(Rates rates) {
        Optional<Rates> foundRate = repository.findBySeason(rates.getSeason());
        if (foundRate.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Esta temporada ya existe,Prueba actualizandola"), HttpStatus.BAD_REQUEST);
        }
        if (rates.validateDate(rates.getStartDate())) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "La fecha de inicio de la temporada es invalida"), HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(new ApiResponse(repository.saveAndFlush(rates),HttpStatus.OK, true, "Nueva temporada creada"), HttpStatus.OK);
    }


    public ResponseEntity<ApiResponse> getAll() {
        return new ResponseEntity<>(new ApiResponse(repository.findAll(), HttpStatus.OK, false, "Tarifas registradas"), HttpStatus.OK);
    }
}
