package hr.ivlahek.sample.store.persistence.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "product")
@Data
@Where(clause = "deleted = false")
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sku")
    private String sku;

    @Column(name = "price")
    private double price;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "date_updated")
    private Date dateUpdated;

    @Column(name = "deleted")
    private boolean deleted;

    public void setDeleted() {
        deleted = true;
    }

    public void setCreated() {
        deleted = false;
    }

    public Product() {
        deleted = false;
    }
}
