package hr.ivlahek.sample.store.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "placed_order")
public class PlacedOrder {
    @Id
    @SequenceGenerator(name = "placed_order_sequence", sequenceName = "placed_order_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "placed_order_sequence")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "date_created")
    private Date dateCreated;

    @OneToMany(mappedBy = "placedOrder")
    private List<PlacedOrderItem> placedOrderItems = new ArrayList<>();

    public List<PlacedOrderItem> getPlacedOrderItems() {
        return placedOrderItems;
    }

    public void setPlacedOrderItems(List<PlacedOrderItem> placedOrderItems) {
        this.placedOrderItems = placedOrderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
