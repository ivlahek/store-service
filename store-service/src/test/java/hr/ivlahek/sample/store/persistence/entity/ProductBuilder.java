package hr.ivlahek.sample.store.persistence.entity;

import hr.ivlahek.sample.store.client.product.EntityDefaults;

import java.util.Date;

public final class ProductBuilder {
    private Long id;
    private String name = EntityDefaults.Product1Defaults.NAME;
    private double price = EntityDefaults.Product1Defaults.PRICE;
    private String sku = EntityDefaults.Product1Defaults.SKU;
    private Date dateCreated = EntityDefaults.Product1Defaults.DATE_CREATED;
    private Date dateUpdated = EntityDefaults.Product1Defaults.DATE_UPDATED;
    private boolean deleted = false;

    private ProductBuilder() {
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public static ProductBuilder aProduct1() {
        return aProduct();
    }

    public static ProductBuilder aProduct2() {
        return aProduct()
                .withDateCreated(EntityDefaults.Product2Defaults.DATE_CREATED)
                .withDateUpdated(EntityDefaults.Product2Defaults.DATE_UPDATED)
                .withName(EntityDefaults.Product2Defaults.NAME)
                .withSku(EntityDefaults.Product2Defaults.SKU)
                .withPrice(EntityDefaults.Product2Defaults.PRICE);
    }

    public static ProductBuilder aDeletedProduct() {
        return aProduct()
                .withDateCreated(EntityDefaults.Product3Defaults.DATE_CREATED)
                .withDateUpdated(EntityDefaults.Product3Defaults.DATE_UPDATED)
                .withName(EntityDefaults.Product3Defaults.NAME)
                .withSku(EntityDefaults.Product3Defaults.SKU)
                .deleted()
                .withPrice(EntityDefaults.Product3Defaults.PRICE);

    }


    public ProductBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public ProductBuilder deleted() {
        this.deleted = true;
        return this;
    }

    public ProductBuilder withDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public Product build() {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDeleted(deleted);
        product.setPrice(price);
        product.setSku(sku);
        product.setDateCreated(dateCreated);
        product.setDateUpdated(dateUpdated);
        return product;
    }

    public ProductBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

}
