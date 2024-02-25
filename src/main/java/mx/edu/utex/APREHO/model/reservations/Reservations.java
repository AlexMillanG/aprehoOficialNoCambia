package mx.edu.utex.APREHO.model.reservations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.paymentHistory.PaymentHistory;
import mx.edu.utex.APREHO.model.people.People;
import mx.edu.utex.APREHO.model.room.Room;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    @Column(columnDefinition = "DATE",nullable = false)
    private LocalDate checkin;
    @Column(columnDefinition = "DATE",nullable = false)
    private LocalDate checkout;
    @Column(nullable = false)
    private  Double discountQuantity;
    @Column(nullable = false)
    private  Boolean discount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peopleId")
    private People people;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    @OneToMany(mappedBy = "reservations", fetch = FetchType.LAZY)
    private Set<PaymentHistory> paymentHistory;



}
