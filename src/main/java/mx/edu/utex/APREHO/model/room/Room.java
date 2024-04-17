package mx.edu.utex.APREHO.model.room;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.images.Images;
import mx.edu.utex.APREHO.model.paymentHistory.PaymentHistory;
import mx.edu.utex.APREHO.model.reservations.ReservationsBean;
import mx.edu.utex.APREHO.model.roomType.RoomType;

import java.util.Set;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @Column(length = 45, nullable = false)
    private String roomName;
    @Column(length = 45, nullable = false)
    private String status;
    @Column(nullable = false)
    private int peopleQuantity;
    @Column(length = 300, nullable = false)
    private String description;


    public Room(Long roomId,String roomName ,String status, int peopleQuantity, String description, RoomType roomType, Hotel hotel) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.status = status;
        this.peopleQuantity = peopleQuantity;
        this.description = description;
        this.roomType = roomType;
        this.hotel = hotel;
    }


    public Room(String roomName ,String status, int peopleQuantity, String description, RoomType roomType, Hotel hotel) {
        this.roomName = roomName;
        this.status = status;
        this.peopleQuantity = peopleQuantity;
        this.description = description;
        this.roomType = roomType;
        this.hotel = hotel;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private Set<ReservationsBean> reservations;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomTypeId")
    private RoomType roomType;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "rooms")
    private Set<Images> images;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @JsonIgnore
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PaymentHistory> paymentHistories;
}
