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




    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> save(DtoProducts product){
        // Verificar si el hotel asociado al producto existe
        Optional<Hotel> foundHotel = hotelRepository.findById(product.getHotelId());
        if (foundHotel.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "No se encontró el hotel relacionado"), HttpStatus.NOT_FOUND);
        }

        // Verificar si el producto ya existe
        Optional<Products> foundProduct = repository.findById(product.getProductId());
        if (foundProduct.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "El producto ya existe"), HttpStatus.BAD_REQUEST);
        }

        // Crear una instancia de Products a partir de DtoProducts
        Products products = product.toEntity();

        // Obtener el hotel asociado al producto
        Hotel hotel = foundHotel.get();

        Set<Hotel> hotels = new HashSet<>();
        hotels.add(hotel);

        // Asignar el hotel al producto
        products.setHotel(hotels);





        // Guardar el producto en la base de datos
        Products savedProduct = repository.save(products);




        /*
         for (User user : hotel.getUser()) {
                    Optional<User> foundUser = userRepository.findById(user.getUserId());
                    if (!foundUser.isPresent()) {
                        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Error, no se ha encontrado el usuario asociado"), HttpStatus.BAD_REQUEST);


        */

        // Devolver respuesta de éxito
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "Producto registrado"), HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveTres(Products products){
        Optional<Hotel> foundHotel = hotelRepository.findById(products.getHotelId());
        if (foundHotel.isEmpty())
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se ha encontrado el hotel asociado"), HttpStatus.BAD_REQUEST);

        Products products1 = new Products();

        products1.setProductName(products.getProductName());
        products1.setProductDescription(products.getProductDescription());
        products1.setPrice(products.getPrice());
        products1.setQuantity(products.getQuantity());

        Hotel hotel = new Hotel();
        hotel.setHotelId(products.getHotelId());
        Set<Hotel> hotels = new HashSet<>();

        products1.setHotel(hotels);
        // Usar persist() en lugar de saveAndFlush()
        repository.save(products1);
        entityManager.flush(); // Forzar la sincronización con la base de datos

        // Obtener el ID del producto después de guardarlo
        Long productId = products1.getProductId();
        Long hotelId = products.getHotelId();

        System.err.println("product: " + productId);
        System.err.println("hotel: " + hotelId);


        // Crear la relación entre el producto y el hotel en la tabla hotelproducts

        return new ResponseEntity<>(new ApiResponse(products1,HttpStatus.OK, false, "Guardado con éxito"), HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse>asosiationHotel(Long hotelId, Long productId){
        repository.saveHotelProducts(productId,hotelId);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,false,"asociado con exito"),HttpStatus.OK);
    }



    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveDosTres(Products products) {
        Optional<Products> foundProduct = repository.findById(products.getProductId());
        if (foundProduct.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Ya se ha registrado ese producto"), HttpStatus.BAD_REQUEST);
        }

        Optional<Hotel> foundHotel = hotelRepository.findById(products.getHotelId());
        if (foundHotel.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "No se ha encontrado el hotel asociado"), HttpStatus.BAD_REQUEST);
        }

        // Obtener el hotel asociado al producto
        Hotel hotel = foundHotel.get();

        // Asignar el hotel al producto
        products.setHotel(Collections.singleton(hotel));

        // Guardar el producto en la base de datos
        repository.saveAndFlush(products);

        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,false,"Guardado con éxito"),HttpStatus.OK);
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
