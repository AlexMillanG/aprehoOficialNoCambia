package mx.edu.utex.APREHO.model.rates;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.roomType.RoomType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratesId;
    @Column(length = 45, nullable = false)
    private String season;
    @Column(nullable = false)
    private  Double price;
    @Column(columnDefinition = "DATE",nullable = false)
    private LocalDate startDate;
    @Column(columnDefinition = "DATE",nullable = false)
    private LocalDate endDate;


    @ManyToMany
    @JoinTable(name="ratesroomType",
            joinColumns = @JoinColumn(name = "roomTypeId"),
            inverseJoinColumns = @JoinColumn(name = "ratesId"))
    Set<RoomType> roomType = new HashSet<>();

    public Rates(String season, Double price, LocalDate startDate, LocalDate endDate) {
        this.season = season;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean validateDate(LocalDate ld) {
        LocalDate ld1 = LocalDate.now();
        if (ld.getYear() < ld1.getYear() || ld.getMonthValue() < ld1.getMonthValue() || ld.getDayOfMonth()< ld1.getDayOfMonth()) {
            return false;
        }

        return true;
    }
}
