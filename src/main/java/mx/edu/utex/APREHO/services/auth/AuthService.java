package mx.edu.utex.APREHO.services.auth;

import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.hotel.HotelRepository;
import mx.edu.utex.APREHO.model.people.People;
import mx.edu.utex.APREHO.model.people.PeopleRepository;
import mx.edu.utex.APREHO.model.rol.Rol;
import mx.edu.utex.APREHO.model.rol.RolRepository;
import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.model.user.UserRepository;
import mx.edu.utex.APREHO.security.jwt.JwtProvider;
import mx.edu.utex.APREHO.services.ServicesUser.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;


@Service
@Transactional
public class AuthService {
    private final UserService userService;
    private final JwtProvider provider;
    private final AuthenticationManager manager;
    private final UserRepository repository;
    private final RolRepository rolRepository;
    private final PeopleRepository peopleRepository;
    private  final HotelRepository hotelRepository;

    public AuthService(UserService userService, JwtProvider provider, AuthenticationManager manager, UserRepository repository, RolRepository rolRepository, PeopleRepository peopleRepository, HotelRepository hotelRepository) {
        this.userService = userService;
        this.provider = provider;
        this.manager = manager;
        this.repository = repository;
        this.rolRepository = rolRepository;
        this.peopleRepository = peopleRepository;
        this.hotelRepository = hotelRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse> signIn(String username, String password) {
        try {
            Optional<User> foundUser = userService.findUserByUsername(username);
            if (foundUser.isEmpty())
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "UserNotFound"), HttpStatus.BAD_REQUEST);
            User user = foundUser.get();
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = provider.generateToken(auth);
            Optional<People> people = peopleRepository.findByPeopleId(user.getPeople().getPeopleId());
            if (people.isPresent()) {
            }
            People people1 = people.get();

            Optional<Rol> rolFound=rolRepository.findByRolId(user.getRol().getRolId());
            Rol rol = rolFound.get();

            Hotel hotel=new Hotel();
            for (Hotel hotel1 : user.getHotel()) {
                Optional<Hotel> foundHotel = hotelRepository.findById(hotel1.getHotelId());
                if(foundHotel.isPresent()){
                    hotel=foundHotel.get();
                }

            }
            DtoAuth dtoAuth1 = new DtoAuth(token, people1, user,rol,hotel);
            //Clase DTO -> token, persona, id, user,
            return new ResponseEntity<>(new ApiResponse(dtoAuth1, HttpStatus.OK, false, "Token generado"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String message = "CredentialsMismatch";
            if (e instanceof DisabledException)
                message = "UserDisabled";
            if (e instanceof AccountExpiredException)
                message = "Expiro";
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, message), HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> loggin(String pass, String email) {
        Optional<User> foundUser = repository.loggin(pass, email);

        if (foundUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(foundUser, HttpStatus.OK, false, "Usuario encontrado"), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, false, "Usuario o contraseña Incorrectas"), HttpStatus.BAD_REQUEST);

        }
    }

}
