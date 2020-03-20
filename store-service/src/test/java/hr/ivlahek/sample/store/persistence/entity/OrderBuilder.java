package hr.ivlahek.sample.store.persistence.entity;

import hr.ivlahek.sample.store.client.product.EntityDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class OrderBuilder {
    private Long id;
    private String email = EntityDefaults.PlacedOrder1Defaults.EMAIL;
    private double totalPrice = EntityDefaults.PlacedOrder1Defaults.TOTAL_PRICE;
    private Date dateCreated = EntityDefaults.PlacedOrder1Defaults.DATE_CREATED;
    private List<OrderItem> orderItems = new ArrayList<>();

    private OrderBuilder() {
    }

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public static OrderBuilder anOrder1() {
        return new OrderBuilder();
    }


    public static OrderBuilder anOrder2() {
        return new OrderBuilder()
                .withEmail(EntityDefaults.PlacedOrder2Defaults.EMAIL)
                .withTotalPrice(EntityDefaults.PlacedOrder2Defaults.TOTAL_PRICE)
                .withDateCreated(EntityDefaults.PlacedOrder2Defaults.DATE_CREATED);
    }


    public OrderBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public OrderBuilder withTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderBuilder withDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public OrderBuilder withPlacedOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setId(id);
        order.setEmail(email);
        order.setTotalPrice(totalPrice);
        order.setDateCreated(dateCreated);
        order.setOrderItems(orderItems);
        return order;
    }

    public OrderBuilder addPlacedOrderItem(OrderItem placedOrder) {
        this.orderItems.add(placedOrder);
        return this;
    }
}
