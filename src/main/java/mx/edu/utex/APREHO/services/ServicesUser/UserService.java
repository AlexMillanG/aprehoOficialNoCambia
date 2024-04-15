package mx.edu.utex.APREHO.services.ServicesUser;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
//import mx.edu.utex.APREHO.model.vhotel.HotelRepository;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.hotel.HotelRepository;
import mx.edu.utex.APREHO.model.people.People;
import mx.edu.utex.APREHO.model.people.PeopleRepository;
import mx.edu.utex.APREHO.model.rol.Rol;
import mx.edu.utex.APREHO.model.rol.RolRepository;
import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.model.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PeopleRepository peopleRepository;
    private final HotelRepository hotelRepository;
    private final RolRepository rolRepository;

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveReceptionist(User user) {
        if (!user.isValid(user.getEmail(), user.getPassword())) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Uno o algunos de los campos estan vacios"), HttpStatus.BAD_REQUEST);
        }

        if (!user.getPeople().isValid(user.getPeople().getName(), user.getPeople().getLastname(), user.getPeople().getSurname(), user.getPeople().getSex(), user.getPeople().getBirthday(), user.getPeople().getCurp())) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Uno o algunos de los campos estan vacios"), HttpStatus.BAD_REQUEST);
        }
        Optional<User> foundUser = repository.findByEmail(user.getEmail());
        if (foundUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Este correo ya se ha registrado previamente"), HttpStatus.BAD_REQUEST);
        } else {
            if (user.getPeople() != null) {
                Optional<People> foundPeople = peopleRepository.findByCurp(user.getPeople().getCurp());
                if (!foundPeople.isPresent()) {
                    peopleRepository.saveAndFlush(user.getPeople());
                } else {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "El curp ya esta registrado"), HttpStatus.BAD_REQUEST);

                }
            } else {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No has ingresado a una Persona para este Usuario"), HttpStatus.BAD_REQUEST);

            }
            if (user.getRol() != null) {
                Optional<Rol> foundRol = rolRepository.findByRolName(user.getRol().getRolName());
                if (!foundRol.isPresent()) {
                    rolRepository.saveAndFlush(user.getRol());
                } else {
                    user.getRol().setRolId(foundRol.get().getRolId());
                }
            } else {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No has ingresado a un ROL para este Usuario"), HttpStatus.BAD_REQUEST);

            }

            if (user.getHotel() != null) {
                Hotel hotel=new Hotel();
                for (Hotel hotel1 : user.getHotel()) {
                    Optional<Hotel> foundHotel = hotelRepository.findById(hotel1.getHotelId());
                    if(foundHotel.isPresent()){
                        hotel=foundHotel.get();
                    }
                  
                }
                hotel.setUser((Set<User>) user);
                hotelRepository.saveAndFlush(hotel);

            } else {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No has ingresado a un ROL para este Usuario"), HttpStatus.BAD_REQUEST);

            }


        }

        return new ResponseEntity<>(new ApiResponse(repository.saveAndFlush(user), HttpStatus.OK, false, "usuario creado exitosamente"), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> updateReceptionist(User newUser) {
        try {
            Optional<User> existingUserOpt = repository.findById(newUser.getUserId());

            if (!existingUserOpt.isPresent()) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "Usuario no encontrado"), HttpStatus.NOT_FOUND);
            }

            User existingUser = existingUserOpt.get();

            if (!existingUser.getEmail().equals(newUser.getEmail())) {
                Optional<User> userWithEmail = repository.findByEmail(newUser.getEmail());
                if (userWithEmail.isPresent()) {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "El correo electrónico ya está registrado"), HttpStatus.BAD_REQUEST);
                }
                existingUser.setEmail(newUser.getEmail());
            }

            People existingPeople = existingUser.getPeople();
            People newPeople = newUser.getPeople();
            if (newPeople != null) {
                if (newPeople.isValid(newPeople.getName(), newPeople.getLastname(), newPeople.getSurname(), newPeople.getSex(), newPeople.getBirthday(), newPeople.getCurp())) {
                    existingPeople.setName(newPeople.getName());
                    existingPeople.setLastname(newPeople.getLastname());
                    existingPeople.setSurname(newPeople.getSurname());
                    existingPeople.setSex(newPeople.getSex());
                    existingPeople.setBirthday(newPeople.getBirthday());
                    existingPeople.setCurp(newPeople.getCurp());
                } else {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Uno o varios campos de la persona son inválidos"), HttpStatus.BAD_REQUEST);
                }
            }

            Rol existingRol = existingUser.getRol();
            Rol newRol = newUser.getRol();
            if (newRol != null) {
                Optional<Rol> rolWithName = rolRepository.findByRolName(newRol.getRolName());
                if (!rolWithName.isPresent()) {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "El rol especificado no existe"), HttpStatus.BAD_REQUEST);
                }
                existingRol.setRolName(newRol.getRolName());
            }

            Set<Hotel> existingHotels = existingUser.getHotel();
            Set<Hotel> newHotels = newUser.getHotel();
            if (newHotels != null) {
                for (Hotel newHotel : newHotels) {
                    Optional<Hotel> hotelOpt = hotelRepository.findById(newHotel.getHotelId());
                    if (!hotelOpt.isPresent()) {
                        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Uno o más hoteles especificados no existen"), HttpStatus.BAD_REQUEST);
                    }
                    existingHotels.add(hotelOpt.get());
                }
            }

            repository.saveAndFlush(existingUser);

            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Usuario actualizado exitosamente"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> save(User user) {
        if (!user.isValid(user.getEmail(), user.getPassword())) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Uno o algunos de los campos estan vacios"), HttpStatus.BAD_REQUEST);
        }

        if (user.getPeople().isValid(user.getPeople().getName(), user.getPeople().getLastname(), user.getPeople().getSurname(), user.getPeople().getSex(), user.getPeople().getBirthday(), user.getPeople().getCurp())) {
            Optional<User> foundUser = repository.findByEmail(user.getEmail());
            if (foundUser.isPresent()) {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Este correo ya se ha registrado previamente"), HttpStatus.BAD_REQUEST);
            } else {
                if (user.getPeople() != null) {
                    Optional<People> foundPeople = peopleRepository.findByCurp(user.getPeople().getCurp());
                    if (!foundPeople.isPresent()) {
                        peopleRepository.saveAndFlush(user.getPeople());
                    } else {
                        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "El curp ya esta registrado"), HttpStatus.BAD_REQUEST);

                    }
                } else {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No has ingresado a una Persona para este Usuario"), HttpStatus.BAD_REQUEST);

                }
                if (user.getRol() != null) {
                    Optional<Rol> foundRol = rolRepository.findByRolName(user.getRol().getRolName());
                    if (!foundRol.isPresent()) {
                        rolRepository.saveAndFlush(user.getRol());
                    } else {
                        user.getRol().setRolId(foundRol.get().getRolId());
                    }
                } else {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No has ingresado a un ROL para este Usuario"), HttpStatus.BAD_REQUEST);

                }
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Uno o varios de los campos estan vacios"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse(repository.saveAndFlush(user), HttpStatus.OK, false, "usuario creado exitosamente"), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse> getAll() {
        List<User> users = repository.findAll();
        return new ResponseEntity<>(new ApiResponse(users, HttpStatus.OK, false, "Usuarios registrados"), HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> update(User user) {
        if (!user.isValid(user.getEmail(), user.getPassword())) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Uno o algunos de los campos estan vacios"), HttpStatus.BAD_REQUEST);
        }
        if (user.getPeople().isValid(user.getPeople().getName(), user.getPeople().getLastname(), user.getPeople().getSurname(), user.getPeople().getSex(), user.getPeople().getBirthday(), user.getPeople().getCurp())) {
            Optional<User> foundUser = repository.findById(user.getUserId());
            if (foundUser.isPresent()) {
                if (user.getPeople() != null) {
                    Optional<People> foundPeople = peopleRepository.findById(user.getPeople().getPeopleId());
                    if (foundPeople.isPresent()) {
                        if (user.getRol() != null) {
                            Optional<Rol> foundRol = rolRepository.findByRolName(user.getRol().getRolName());
                            if (!foundRol.isPresent()) {
                                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "El rol no es correcto"), HttpStatus.BAD_REQUEST);
                            }
                            user.setRol(foundRol.get());
                        } else {
                            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No has ingresado a un ROL para este Usuario"), HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Los datos no son correctos"), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No has ingresado a una Persona para este Usuario"), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Este usuario no existe"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Uno o algunos de los campos estan vacios"), HttpStatus.BAD_REQUEST);

        }


        return new ResponseEntity<>(new ApiResponse(repository.saveAndFlush(user), HttpStatus.OK, false, "Usuario Actualizado exitosamente"), HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(User user) {

        Optional<User> foundUser = repository.findById(user.getUserId());
        if (foundUser.isPresent()) {
            if (user.getPeople() != null) {
                Optional<People> foundPeople = peopleRepository.findById(user.getPeople().getPeopleId());
                if (foundPeople.isPresent()) {
                    People people = foundPeople.get();
                    peopleRepository.delete(people);
                    User user1 = foundUser.get();
                    repository.delete(user1);
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "usuario Eliminado exitosamente"), HttpStatus.OK);

                } else {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No hemos encontrado a la persona"), HttpStatus.BAD_REQUEST);

                }
            } else {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Esta persona no existe"), HttpStatus.BAD_REQUEST);

            }
        }

        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Este usuario no existe"), HttpStatus.BAD_REQUEST);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> findOne(Long id) {
        Optional<User> foundUser = repository.findById(id);
        if (foundUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(repository.findOne(id), HttpStatus.OK, false, "Usuario encontrado"), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, false, "Usuario no encontrado"), HttpStatus.BAD_REQUEST);

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

    @Transactional(rollbackFor = {SQLException.class})
    public Optional<User> findUserByUsernameAndPassword(String pass, String email) {
        return repository.loggin(pass, email);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String email) {
        return repository.findFirstByEmail(email);
    }
}

