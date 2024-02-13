package mx.edu.utex.APREHO.model.ratesBean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.productsBean.Products;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomType;

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

}
