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

    public Products toEntity(){
        return new Products(productName, price, productDescription, quantity);
    }
public Products toEntityId(){
return new Products(productId, productName, price, productDescription, quantity);
}
}
