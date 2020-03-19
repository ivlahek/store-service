package hr.ivlahek.sample.store.persistence.entity;

public final class PlacedOrderProductBuilder {
    private Order order;
    private Product product;
    private int quantity = 1;

    private PlacedOrderProductBuilder() {
    }

    public static PlacedOrderProductBuilder aPlacedOrderItem() {
        return new PlacedOrderProductBuilder();
    }

    public PlacedOrderProductBuilder withPlacedOrder(Order order) {
        this.order = order;
        return this;
    }

    public PlacedOrderProductBuilder withProduct(Product product) {
        this.product = product;
        return this;
    }

    public PlacedOrderProductBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderItem build() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        return orderItem;
    }
}
