package mx.edu.utex.APREHO.model.hotel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.images.Images;
import mx.edu.utex.APREHO.model.paymentHistory.PaymentHistory;
import mx.edu.utex.APREHO.model.products.Products;
import mx.edu.utex.APREHO.model.reservations.ReservationsBean;
import mx.edu.utex.APREHO.model.room.Room;
import mx.edu.utex.APREHO.model.roomType.RoomType;
import mx.edu.utex.APREHO.model.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hotel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    @Column(length = 45, nullable = false)
    private String hotelName;
    @Column(length = 100, nullable = false)
    private String address;
    @Column(length = 100, nullable = false)
    private String email;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String phone;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;


    public Hotel(Long hotelId, String hotelName, String address, String email, String phone, String city,Set<User> user, String description) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.user = user;
        this.description = description;
    }

    public Hotel(Long hotelId, String hotelName, String address, String email, String phone, String city, String description) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.description = description;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private Set<Room> room;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="hoteluser",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "hotelId"))
    Set<User> user = new HashSet<>();

    @JsonIgnoreProperties("hotels")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="hotel_images",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "images_id"))
    private Set<Images> images;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="hotelproducts",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "hotelId"))
    Set<Products> products = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    private Set<RoomType> roomTypes;


    @JsonIgnore
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReservationsBean> reservations;


    @JsonIgnore
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PaymentHistory> paymentHistory;
}
