package mx.edu.utex.APREHO.controllers.UserControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.UserControllers.Dto.DtoUser;
import mx.edu.utex.APREHO.services.ServicesUser.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin({"*"})
@AllArgsConstructor
public class UserControllers {
    private final UserService service;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody  DtoUser user){
        return service.save(user.toEntity());
    }

    @GetMapping("findAll")
    public ResponseEntity<ApiResponse> findAll(){
        return service.getAll();
    }

}
