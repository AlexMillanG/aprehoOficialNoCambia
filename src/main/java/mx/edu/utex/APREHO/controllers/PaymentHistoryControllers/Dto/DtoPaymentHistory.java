package mx.edu.utex.APREHO.controllers.PaymentHistoryControllers.Dto;

import lombok.Data;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.paymentHistory.PaymentHistory;
import mx.edu.utex.APREHO.model.products.Products;
import mx.edu.utex.APREHO.model.reservations.ReservationsBean;
import mx.edu.utex.APREHO.model.room.Room;
import mx.edu.utex.APREHO.model.user.User;

import java.time.LocalDate;

@Data
public class DtoPaymentHistory {
    private Long paymentHistoryId;
    private LocalDate checkout;
    private Double total;
    private boolean paymentStatus;
    private User user;
    private Hotel hotel;
    private Products products;
    private ReservationsBean reservation;
    public PaymentHistory toEntity(){
        return new PaymentHistory(paymentHistoryId,checkout,total,paymentStatus,products,reservation,hotel,user);
    }
}
