package mx.edu.utex.APREHO.model.hotelBean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.imagesBean.Images;
import mx.edu.utex.APREHO.model.productsBean.Products;
import mx.edu.utex.APREHO.model.roomBean.Room;
import mx.edu.utex.APREHO.model.roomTypeBean.RoomType;
import mx.edu.utex.APREHO.model.userBean.User;

import java.awt.*;
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
    @Column(nullable = false)
    private int phone;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String description;


    public Hotel(Long hotelId, String hotelName, String address, String email, int phone, String city,Set<User> user, String description) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.user = user;
        this.description = description;
    }

    public Hotel(Long hotelId, String hotelName, String address, String email, int phone, String city, String description) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.description = description;
    }

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private Set<Room> room;

    @ManyToMany
    @JoinTable(name="hoteluser",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "hotelId"))
    Set<User> user = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imagesId")
    private Images images;


    @ManyToMany
    @JoinTable(name="hotelproducts",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "hotelId"))
    Set<Products> products = new HashSet<>();

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    private Set<RoomType> roomTypes;


    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", hotelName='" + hotelName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", room=" + room +
                ", user=" + user +
                ", images=" + images +
                ", products=" + products +
                '}';
    }
}
