package mx.edu.utex.APREHO.controllers.ProductsControllers.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.products.Products;

import java.util.Set;

@Data
@NoArgsConstructor
public class DtoProducts {
    private Long productId;
    private String productName;
    private int price;
    private String productDescription;
    private int quantity;
    private Long hotelId;
    private Set<Hotel> hotels;
    public Products toEntity() {
        return new Products(productId,productName, price, productDescription, quantity,hotelId);
    }

    public Products toEntityId() {
        return new Products(productId, productName, price, productDescription, quantity);
    }

    public Products toEntitySave(){

        System.err.println(hotels);
        return new Products(productId,productName,price,productDescription,quantity,hotels);
    }


}
