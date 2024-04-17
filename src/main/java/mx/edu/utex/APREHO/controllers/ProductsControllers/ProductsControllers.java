package mx.edu.utex.APREHO.controllers.ProductsControllers;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.ProductsControllers.Dto.DtoProducts;
import mx.edu.utex.APREHO.controllers.UserControllers.Dto.DtoUser;
import mx.edu.utex.APREHO.services.ServicesProducts.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
public class ProductsControllers {

private final ProductsService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll() {
        return service.getAll();
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody DtoProducts dto) {
        return service.save(dto.toEntity());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id,@RequestBody DtoProducts dto){return service.update(id,dto.toEntityId());}


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable long id){return service.delete(id);}

    @GetMapping("/findByHotelody")
    public ResponseEntity<ApiResponse> findByUserBody(@RequestBody DtoUser dtoUser){
        Long id = dtoUser.getUserId();
        return service.findProductsByHotel(id);
    }
}
