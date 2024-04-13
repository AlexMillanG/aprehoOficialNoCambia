package mx.edu.utex.APREHO.controllers.PaymentHistoryControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.services.ServicesPaymentHistory.PaymentHistoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin({"*"})
@RequestMapping({"/api/paymentStory"})
public class PaymentHistoryControllers {

    private final PaymentHistoryService service;



}
