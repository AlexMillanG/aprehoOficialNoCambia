package mx.edu.utex.APREHO.controllers.ProductsControllers.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utex.APREHO.model.products.Products;

@Data
@NoArgsConstructor
public class DtoProducts {
    private Long productId;
    private String productName;
    private int price;
    private String productDescription;
    private int quantity;
    private Long hotelId;

    public DtoProducts(Long productId, String productName, int price, String productDescription, int quantity, Long hotelId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.hotelId = hotelId;
    }

    /*   public Products toEntity() {
        return new Products(productId,productName, price, productDescription, quantity,hotelId);
    }*/

    public Products toEntityId() {
        return new Products(productId, productName, price, productDescription, quantity);
    }






}
