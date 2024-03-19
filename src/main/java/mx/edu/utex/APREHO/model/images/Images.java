package mx.edu.utex.APREHO.model.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(nullable = false,columnDefinition = "LONGBLOB")
    private byte[] image;


    @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Room> room;


    @JsonIgnoreProperties("images")
    @ManyToMany(mappedBy = "images", fetch = FetchType.LAZY)
    private Set<Hotel> hotels;






    
}

