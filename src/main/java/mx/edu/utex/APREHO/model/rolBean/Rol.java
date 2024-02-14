package mx.edu.utex.APREHO.model.rolBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.userBean.User;

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
    @OneToMany(mappedBy = "rol",fetch = FetchType.EAGER)
    private Set<User> user;

}
