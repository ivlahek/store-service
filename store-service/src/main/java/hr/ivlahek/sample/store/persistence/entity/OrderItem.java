package hr.ivlahek.sample.store.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "order_product")
@Data
@NoArgsConstructor
public class OrderItem {

    @Id
    @SequenceGenerator(name = "order_product_sequence", sequenceName = "order_product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_product_sequence")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "product_id")
    private long productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "product_price")
    private double productPrice;

    public OrderItem(Order order, long productId, int quantity, double productPrice) {
        this.order = order;
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }
}
