package mx.edu.utex.APREHO.services.ServicesUser;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotelBean.HotelRepository;
import mx.edu.utex.APREHO.model.peopleBean.PeopleRepository;
import mx.edu.utex.APREHO.model.rolBean.Rol;
import mx.edu.utex.APREHO.model.rolBean.RolRepository;
import mx.edu.utex.APREHO.model.userBean.User;
import mx.edu.utex.APREHO.model.userBean.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PeopleRepository peopleRepository;
    private final HotelRepository hotelRepository;
    private final RolRepository rolRepository;

  /*  @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> save(User user) {
        Optional<User> foundUser(repository.findByUsername(user.getUsername()));

    }*/

}
