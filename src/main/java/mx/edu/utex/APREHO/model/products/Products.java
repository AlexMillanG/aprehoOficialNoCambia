package mx.edu.utex.APREHO.model.products;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utex.APREHO.model.hotel.Hotel;
import mx.edu.utex.APREHO.model.paymentHistory.PaymentHistory;

import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(length = 45, nullable = false)
    private String productName;
    @Column(nullable = false)
    private int price;
    @Column(length = 150, nullable = false)
    private String productDescription;
    @Column(nullable = false)
    private int quantity;

    public Products(String productName, int price, String productDescription, int quantity) {
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.quantity = quantity;
    }

    public Products(Long productId, String productName, int price, String productDescription, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.productDescription = productDescription;
        this.quantity = quantity;
    }

    @ManyToMany(mappedBy = "products")
    Set<Hotel> hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentHistoryId")
    private PaymentHistory paymentHistory;


}
