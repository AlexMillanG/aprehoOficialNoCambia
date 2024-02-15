package mx.edu.utex.APREHO.controllers.ProductsControllers.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utex.APREHO.model.productsBean.Products;

@Data
@NoArgsConstructor
public class DtoProducts {
    private Long productId;
    private String productName;
    private int price;
    private String productDescription;
    private int quantity;

public Products toEntity(){
return new Products(productId, productName, price, productDescription, quantity);
}
}
