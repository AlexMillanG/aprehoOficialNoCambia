package mx.edu.utex.APREHO.controllers.PaymentHistoryControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.PaymentHistoryControllers.Dto.DtoPaymentHistory;
import mx.edu.utex.APREHO.model.paymentHistory.PaymentHistory;
import mx.edu.utex.APREHO.model.paymentHistory.PaymentHistoryRepository;
import mx.edu.utex.APREHO.services.ServicesPaymentHistory.PaymentHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin({"*"})
@RequestMapping("/api/paymentStory")
public class PaymentHistoryControllers {

    private final PaymentHistoryService service;
    private final PaymentHistoryRepository repository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody DtoPaymentHistory dtoPaymentHistory){
        return service.savePayment(dtoPaymentHistory.toEntity());
    }
    @GetMapping("/")
    public ResponseEntity<ApiResponse> g() {
        Iterable<PaymentHistory> datos = repository.findAll(); // Reemplaza 'TipoDeTuEntidad' con el tipo real de tus entidades
        ApiResponse response = new ApiResponse(datos, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }



}
