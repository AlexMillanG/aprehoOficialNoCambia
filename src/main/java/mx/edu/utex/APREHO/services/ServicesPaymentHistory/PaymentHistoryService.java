package mx.edu.utex.APREHO.services.ServicesPaymentHistory;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.paymentHistory.PaymentHistory;
import mx.edu.utex.APREHO.model.paymentHistory.PaymentHistoryRepository;
import mx.edu.utex.APREHO.model.products.ProductRepository;
import mx.edu.utex.APREHO.model.products.Products;
import mx.edu.utex.APREHO.model.reservations.ReservationsBean;
import mx.edu.utex.APREHO.model.reservations.ReservationsRepository;
import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.model.user.UserRepository;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class PaymentHistoryService {
    private final PaymentHistoryRepository repository;
    private final ProductRepository productRepository;
    private final ReservationsRepository reservationsRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = {SqlScriptException.class})
    public ResponseEntity<ApiResponse> savePayment(PaymentHistory paymentHistory){
        //validación que este asociada a una reservación
        Optional<ReservationsBean> foundReservation = reservationsRepository.findById(paymentHistory.getReservations().getReservationId());
        if (foundReservation.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"No se ha encontrado la reservación asociada"), HttpStatus.BAD_REQUEST);

        //valida que este asociada a un usuario
        Optional<User> foundUser = userRepository.findById(paymentHistory.getUser().getUserId());
        if (foundUser.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"No se encontró el usuario asociado"), HttpStatus.NOT_FOUND);



        //validación para procesar los productos
        Set<Products> productsSet = paymentHistory.getProducts();
        for (Products product : productsSet) {
            Optional<Products> foundProduct = productRepository.findById(product.getProductId());
            if (foundProduct.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el producto"), HttpStatus.NOT_FOUND);
            }
            if (foundProduct.get().getQuantity() < 1) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No hay stock del producto"), HttpStatus.BAD_REQUEST);
            }
            if (foundProduct.get().getQuantity() < product.getQuantity()) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No hay suficiente cantidad para la compra, stock: " + foundProduct.get().getQuantity()), HttpStatus.BAD_REQUEST);
            }

            // Se reduce la cantidad del stock
            int requestedQuantity = product.getQuantity();
            int currentStock = foundProduct.get().getQuantity();
            int newStock = currentStock - requestedQuantity;
            foundProduct.get().setQuantity(newStock);

            // Se actualiza el producto en la base de datos
            productRepository.save(foundProduct.get());
        }



        return new ResponseEntity<>(new ApiResponse(repository.save(paymentHistory),HttpStatus.OK,false,"guardado con exito"),HttpStatus.OK);
    }


}

