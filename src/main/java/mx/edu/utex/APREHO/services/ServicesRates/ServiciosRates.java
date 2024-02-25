package mx.edu.utex.APREHO.services.ServicesRates;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.rates.RatesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ServiciosRates {
    private final RatesRepository repository;

    public ResponseEntity<ApiResponse> saveRate(){
        return null;
    }

}
