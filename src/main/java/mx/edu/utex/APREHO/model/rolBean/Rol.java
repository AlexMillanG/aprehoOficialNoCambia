package mx.edu.utex.APREHO.model.rolBean;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.userBean.User;

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



    @OneToOne(mappedBy = "rol", cascade = CascadeType.ALL)
    private User user;

}
