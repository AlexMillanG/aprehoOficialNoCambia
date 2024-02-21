package mx.edu.utex.APREHO.services.ServicesUser;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.UserControllers.Dto.DtoUser;
import mx.edu.utex.APREHO.model.hotelBean.HotelRepository;
import mx.edu.utex.APREHO.model.peopleBean.People;
import mx.edu.utex.APREHO.model.peopleBean.PeopleRepository;
import mx.edu.utex.APREHO.model.rolBean.Rol;
import mx.edu.utex.APREHO.model.rolBean.RolRepository;
import mx.edu.utex.APREHO.model.userBean.User;
import mx.edu.utex.APREHO.model.userBean.UserRepository;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PeopleRepository peopleRepository;
    private final HotelRepository hotelRepository;
    private final RolRepository rolRepository;

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> save(User user) {
        Optional<User> foundUser = repository.findByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Este usuario ya existe"), HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>(new ApiResponse(repository.saveAndFlush(user), HttpStatus.OK, false, "usuario creado exitosamente"), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> getAll() {
        List<User> users = repository.getUser();

        return new ResponseEntity<>(new ApiResponse(users, HttpStatus.OK, false, "Usuarios registrados"), HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> update(User user) {
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
        return new ResponseEntity<>(new ApiResponse(repository.saveAndFlush(user), HttpStatus.OK, false, "Usuario Actualizado exitosamente"), HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(User user) {
        Optional<User> foundUser = repository.findByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            if (user.getPeople() != null) {
                Optional<People> foundPeople = peopleRepository.findByCurp(user.getPeople().getCurp());
                if (foundPeople.isPresent()) {
                    People people = foundPeople.get();
                    peopleRepository.delete(people);
                    repository.delete(user);
                    return new ResponseEntity<>(new ApiResponse( HttpStatus.OK, false, "usuario Eliminado exitosamente"), HttpStatus.OK);

                }else {
                    return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No hemos encontrado a la persona"), HttpStatus.BAD_REQUEST);

                }
            }else {
                return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Este usuario no existe"), HttpStatus.BAD_REQUEST);

            }
        }else{
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Este usuario no existe"), HttpStatus.BAD_REQUEST);

        }

    }

}

