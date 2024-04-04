package mx.edu.utex.APREHO.config;

import mx.edu.utex.APREHO.model.people.People;
import mx.edu.utex.APREHO.model.people.PeopleRepository;
import mx.edu.utex.APREHO.model.rol.Rol;
import mx.edu.utex.APREHO.model.rol.RolRepository;
import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.model.user.UserRepository;
import org.apache.catalina.mbeans.RoleMBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class InitialConfig implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UserRepository userRepository;
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;


    public InitialConfig(RolRepository rolRepository, UserRepository userRepository, PeopleRepository personRepository, PasswordEncoder encoder) {
        this.rolRepository = rolRepository;
        this.userRepository = userRepository;
        this.peopleRepository = personRepository;
        this.passwordEncoder = encoder;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void run(String... args) throws Exception {
        Rol adminRole = getOrSaveRole(new Rol( "ADMIN_ROLE"));
        getOrSaveRole(new Rol("USER_ROLE"));
        getOrSaveRole(new Rol("RECEPTIONIST_ROLE"));
        //Crear un usuario para que puedan iniciar sesión (person, user, user_role)
        People person = getOrSavePerson(
                new People("alex", "millan", "guillen","Hombre",
                        LocalDate.of(2004, 6, 10), "MOVM980119HM")
        );
        User user = getOrSaveUser(
                new User("admin", passwordEncoder.encode("admin"), person)
        );

       // saveUserRoles(user.getId(), adminRole.getId());
    }

    @Transactional
    public Rol getOrSaveRole(Rol role) {
        Optional<Rol> foundRole = rolRepository.findByRolName(role.getRolName());
        return foundRole.orElseGet(() -> rolRepository.saveAndFlush(role));
    }
    @Transactional
    public People getOrSavePerson(People person) {
        Optional<People> foundPerson = peopleRepository.findByCurp(person.getCurp());
        return foundPerson.orElseGet(() -> peopleRepository.saveAndFlush(person));
    }
    @Transactional
    public User getOrSaveUser(User user) {
        Optional<User> foundUser = userRepository.findFirstByEmail(user.getEmail());
        return foundUser.orElseGet(() -> userRepository.saveAndFlush(user));
    }
    /*@Transactional
    public void saveUserRoles(Long id, Long roleId) {
        Long userRoleId = userRepository.getIdUserRoles(id, roleId);
        if (userRoleId == null)
            userRepository.saveUserRole(id, roleId);
    }*/
}
