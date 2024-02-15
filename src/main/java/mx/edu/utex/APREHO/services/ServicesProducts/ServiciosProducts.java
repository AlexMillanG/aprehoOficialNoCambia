package mx.edu.utex.APREHO.services.ServicesProducts;

import lombok.AllArgsConstructor;
import mx.edu.utex.APREHO.config.ApiResponse;
import mx.edu.utex.APREHO.model.productsBean.ProductRepository;
import mx.edu.utex.APREHO.model.productsBean.Products;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ServiciosProducts {
    private final ProductRepository repository;

    public ResponseEntity<ApiResponse> getAll(){
        return new ResponseEntity<>(new ApiResponse(repository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
public ResponseEntity<ApiResponse> save(Products product){
        Optional<Products> foundProducto = repository.findByProductName(product.getProductName());
        if(foundProducto.isPresent()){
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "El producto ya existe"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(repository.save(product), HttpStatus.OK, false, "Producto registrado"), HttpStatus.OK);
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
