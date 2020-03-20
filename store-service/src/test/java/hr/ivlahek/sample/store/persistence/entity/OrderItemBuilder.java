package hr.ivlahek.sample.store.persistence.entity;

public final class OrderItemBuilder {
    private Order order;
    private long productId;
    private double productPrice;
    private int quantity = 1;

    private OrderItemBuilder() {
    }

    public static OrderItemBuilder aPlacedOrderItem() {
        return new OrderItemBuilder();
    }

    public OrderItemBuilder withPlacedOrder(Order order) {
        this.order = order;
        return this;
    }

    public OrderItemBuilder withProduct(Product product) {
        this.productId = product.getId();
        this.productPrice = product.getPrice();
        return this;
    }

    public OrderItemBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderItem build() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProductPrice(productPrice);
        orderItem.setProductId(productId);
        orderItem.setQuantity(quantity);
        return orderItem;
    }
}
