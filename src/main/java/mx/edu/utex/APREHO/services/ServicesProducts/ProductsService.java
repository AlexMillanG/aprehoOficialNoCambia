package mx.edu.utex.APREHO.services.ServicesProducts;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.controllers.ProductsControllers.Dto.DtoProducts;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.hotel.HotelRepository;
import mx.edu.utex.APREHO.model.products.ProductRepository;
import mx.edu.utex.APREHO.model.products.Products;
import mx.edu.utex.APREHO.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class ProductsService {
    private final ProductRepository repository;
    private final HotelRepository hotelRepository;
    private EntityManager entityManager;

    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(repository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    //se espera un id
    public ResponseEntity<ApiResponse> findProductsByHotel(Long id){
        //se crea un objeto usuario
        Hotel hotel = new Hotel();
        //a este objeto se le setea la id obtenida
        hotel.setHotelId(id);
        //crea una lista de hoteles que coincida con el usuario
        List<Products> foundUsersHotels = repository.findByHotel(hotel);
        //valida que encuentre registros
        if (foundUsersHotels.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND,true,"No se encontraron productos para este hotel"),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse(foundUsersHotels,HttpStatus.OK),HttpStatus.OK);
    }




    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<ApiResponse> saveChido(DtoProducts dtoProducts) {
        Optional<Hotel> foundHotel = hotelRepository.findById(dtoProducts.getHotelId());
        if (foundHotel.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el hotel relacionado"), HttpStatus.NOT_FOUND);

        Optional<Products> foundProduct = repository.findByProductName(dtoProducts.getProductName());
        if (!foundProduct.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "Ya se ha registrado este producto"), HttpStatus.NOT_FOUND);

        Products products = new Products();
        products.setProductDescription(dtoProducts.getProductDescription());
        products.setQuantity(dtoProducts.getQuantity());
        products.setPrice(dtoProducts.getPrice());
        products.setProductName(dtoProducts.getProductName());

        // Obtener el hotel existente desde la base de datos
        Hotel hotel = foundHotel.get();
        // Agregar el hotel al conjunto de hoteles del producto
        Set<Hotel> hotels = new HashSet<>();
        hotels.add(hotel);
        products.setHotel(hotels);

        repository.save(products);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Producto registrado"), HttpStatus.OK);
    }



    @Transactional(rollbackFor = {Exception.class})
    public ResponseEntity<ApiResponse> update(Long id, Products producto) {
        Optional<Products> foundProducto = repository.findById(id);
        if (foundProducto.isPresent()) {
            producto.setProductId(id);
            return new ResponseEntity<>(new ApiResponse(repository.saveAndFlush(producto), HttpStatus.OK, false, "Producto actualizado"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se encontró el producto a actualizar"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Products> foundProducto = repository.findById(id);
        if (foundProducto.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Producto eliminado"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el producto a eliminar"), HttpStatus.NOT_FOUND);
        }
    }

}
