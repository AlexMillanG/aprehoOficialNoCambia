package mx.edu.utex.APREHO.model.people;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.reservations.Reservations;
import mx.edu.utex.APREHO.model.user.User;

import java.util.Set;

@Entity
@Table(name = "people")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long peopleId;
    @Column(length = 45, nullable = false)
    private String name;
    @Column(length = 45, nullable = false)
    private String lastname;
    @Column(length = 18, nullable = false)
    private String curp;


@JsonIgnore
    @OneToOne(mappedBy = "people", cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "people", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Reservations> reservations;
}