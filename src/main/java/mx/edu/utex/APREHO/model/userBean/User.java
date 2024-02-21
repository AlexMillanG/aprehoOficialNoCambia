package mx.edu.utex.APREHO.model.userBean;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.hotelBean.Hotel;
import mx.edu.utex.APREHO.model.peopleBean.People;
import mx.edu.utex.APREHO.model.rolBean.Rol;

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
    private String username;
    @Column(length = 45, nullable = false)
    private String password;


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


    public User(Long userId, String username, String password, Rol rol, People people) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.people = people;
    }

    public User(String username, String password, Rol rol, People people) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.people = people;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                ", people=" + people +
                ", hotel=" + hotel +
                '}';
    }

}
