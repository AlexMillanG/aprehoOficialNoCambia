package mx.edu.utex.APREHO.model.roomType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.rates.Rates;
import mx.edu.utex.APREHO.model.room.Room;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roomType")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    @Column(length = 45, nullable = false)
    private String typeName;

    @Column(length = 6)
    private double price;

    @JsonIgnore
    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();


    @ManyToMany(mappedBy = "roomType")
    Set<Rates> rates;
    @JsonIgnore

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public RoomType(Long roomTypeId, String typeName, Set<Room> rooms, Hotel hotel) {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.rooms = rooms;
        this.hotel = hotel;
    }

    public RoomType(String typeName, Set<Room> rooms, Hotel hotel) {
        this.typeName = typeName;
        this.rooms = rooms;
        this.hotel = hotel;
    }


    @Override
    public String toString() {
        return "RoomType{" +
                "roomTypeId=" + roomTypeId +
                ", typeName='" + typeName + '\'' +
                ", rooms=" + rooms +
                ", rates=" + rates +
                ", hotel=" + hotel +
                '}';
    }
}
