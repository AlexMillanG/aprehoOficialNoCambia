package mx.edu.utex.APREHO.model.imagesBean;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.hotelBean.Hotel;
import mx.edu.utex.APREHO.model.reservationsBean.Reservations;
import mx.edu.utex.APREHO.model.roomBean.Room;

import java.sql.Blob;
import java.util.Set;

@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imagesId;
    @Column(nullable = false)
    private int image;


    @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Room> room;

    @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Hotel> hotel;
}

