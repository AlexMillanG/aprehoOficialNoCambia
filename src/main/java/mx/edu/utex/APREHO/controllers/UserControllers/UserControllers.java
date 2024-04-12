package mx.edu.utex.APREHO.controllers.UserControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.UserControllers.Dto.DtoUser;
import mx.edu.utex.APREHO.model.user.User;
import mx.edu.utex.APREHO.services.ServicesUser.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin({"*"})
@AllArgsConstructor
public class UserControllers {
    private final UserService service;

    private  final PasswordEncoder passwordEncoder;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody  DtoUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return service.save(user.toEntity());
    }

    @GetMapping("/findAll")
    public ResponseEntity<ApiResponse> findAll(){
        ResponseEntity<ApiResponse> users=service.getAll();

        return users;
    }


    @GetMapping("/findOne/{id}")
    private ResponseEntity<ApiResponse> findOne(@PathVariable Long id){
        return service.findOne(id);
    }

    @PutMapping("update")
    public  ResponseEntity<ApiResponse> update(@RequestBody DtoUser dtoUser){
        dtoUser.setPassword(passwordEncoder.encode(dtoUser.getPassword()));
        return  service.update(dtoUser.toEntityId());
    }

    @DeleteMapping("delete")
    public  ResponseEntity<ApiResponse> delete(@RequestBody DtoUser dtoUser){
        return  service.delete(dtoUser.toEntityId());
    }

    @PostMapping("/login")
    public  ResponseEntity<ApiResponse> login(@RequestBody DtoUser dtoUser){
        return service.loggin(dtoUser.getPassword(),dtoUser.getEmail());
    }

}
