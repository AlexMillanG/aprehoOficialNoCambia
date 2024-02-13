package mx.edu.utex.APREHO.model.userBean;

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
    private Rol rol;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "peopleId")
    private People people;

    @ManyToMany(mappedBy = "user")
    Set<Hotel> hotel;

}
