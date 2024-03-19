package mx.edu.utex.APREHO.model.reservations;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ReservationsBean {
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "peopleId")
    private People people;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    @JsonIgnore
    @OneToMany(mappedBy = "reservations", fetch = FetchType.LAZY)
    private Set<PaymentHistory> paymentHistory;

    public ReservationsBean(Long reservationId, LocalDate checkin, LocalDate checkout, Double discountQuantity, Boolean discount, People people, Room room) {
        this.reservationId = reservationId;
        this.checkin = checkin;
        this.checkout = checkout;
        this.discountQuantity = discountQuantity;
        this.discount = discount;
        this.people = people;
        this.room = room;
    }

    public boolean validateDate(LocalDate ld) {
        LocalDate ld1 = LocalDate.now();
        //valida que la fecha que le mandes no sea menor que hoy
        if (ld.getYear() < ld1.getYear() || ld.getMonthValue() < ld1.getMonthValue() || ld.getDayOfMonth()< ld1.getDayOfMonth()) {
            return false;
        }
    return true;
    }

}
