package mx.edu.utex.APREHO.model.rol;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import mx.edu.utex.APREHO.model.user.User;

import java.util.Set;

@Entity
@Table(name = "rol")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolId;
    @Column(length = 45, nullable = false)
    private String rolName;

    @JsonIgnore
    @OneToMany(mappedBy = "rol",fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    private Set<User> user;

    @Override
    public String toString() {
        return "Rol{" +
                "rolId=" + rolId +
                ", rolName='" + rolName + '\'' +
                '}';
    }

    public Rol(String rolName) {
        this.rolName = rolName;
    }

    public Rol(Long rolId) {
        this.rolId = rolId;
    }
}
