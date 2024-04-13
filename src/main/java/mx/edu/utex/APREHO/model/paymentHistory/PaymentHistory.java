package mx.edu.utex.APREHO.model.paymentHistory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.products.Products;
import mx.edu.utex.APREHO.model.reservations.ReservationsBean;
import mx.edu.utex.APREHO.model.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "paymentHistory")
@Getter
@Setter
@NoArgsConstructor
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentHistoryId;
    private LocalDate checkout;
    @Column(nullable = false)
    private  Double total;
    @Column(nullable = false)
    private  Boolean paymentStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Products products;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservationId")
    private ReservationsBean reservations;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public PaymentHistory(Long paymentHistoryId, LocalDate checkout, Double total, Boolean paymentStatus, Products products, ReservationsBean reservations, Hotel hotel, User user) {
        this.paymentHistoryId = paymentHistoryId;
        this.checkout = checkout;
        this.total = total;
        this.paymentStatus = paymentStatus;
        this.products = products;
        this.reservations = reservations;
        this.hotel = hotel;
        this.user = user;
    }
}

