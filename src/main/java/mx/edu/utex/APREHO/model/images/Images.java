package mx.edu.utex.APREHO.model.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.room.Room;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imagesId;
    @Column(nullable = false,columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "room_images",
            joinColumns = @JoinColumn(name = "images_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<Room> rooms;

    public Images() {
        this.rooms = new HashSet<>();
    }

    @JsonIgnoreProperties("images")
    @ManyToMany(mappedBy = "images", fetch = FetchType.LAZY)
    private Set<Hotel> hotels;




    
}

