package hr.ivlahek.sample.store.persistence.entity;

import javax.persistence.*;

@Entity(name = "placed_order_product")
public class PlacedOrderItem {

    @Id
    @SequenceGenerator(name = "placed_order_product_sequence", sequenceName = "placed_order_product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "placed_order_product_sequence")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private PlacedOrder placedOrder;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    public PlacedOrderItem(Product product, PlacedOrder placedOrder, Integer quantity) {
        this.product = product;
        this.placedOrder = placedOrder;
        this.quantity = quantity;
    }

    public PlacedOrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlacedOrder getPlacedOrder() {
        return placedOrder;
    }

    public void setPlacedOrder(PlacedOrder placedOrder) {
        this.placedOrder = placedOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
