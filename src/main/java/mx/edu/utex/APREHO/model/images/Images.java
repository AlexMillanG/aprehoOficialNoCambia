package mx.edu.utex.APREHO.model.images;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.room.Room;

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
    @Column(nullable = false,columnDefinition = "BLOB")
    private byte[] image;


    @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Room> room;

    @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Hotel> hotel;
}

