package mx.edu.utex.APREHO.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.people.People;
import mx.edu.utex.APREHO.model.rol.Rol;

import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(length = 45, nullable = false)
    private String password;

    @Column(length =45, nullable = false)
    private  String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rolId")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "rolId"
    )
    private Rol rol;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "peopleId")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "peopleId"
    )
    private People people;

    @JsonIgnore
    @ManyToMany(mappedBy = "user")
    private Set<Hotel> hotel;


    public User(Long userId,String password,String email, Rol rol, People people) {
        this.userId = userId;
        this.email=email;
        this.password = password;
        this.rol = rol;
        this.people = people;
    }

    public User( String password,  String email, Rol rol, People people) {
        this.password = password;
        this.email=email;
        this.rol = rol;
        this.people = people;
    }

    public boolean isValid(String email, String password){
        if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ) {
            return false;
        }

        this.email = email.trim();
        this.password = password.trim();

        return true;
    }
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                ", people=" + people +
                ", hotel=" + hotel +
                '}';
    }

}
