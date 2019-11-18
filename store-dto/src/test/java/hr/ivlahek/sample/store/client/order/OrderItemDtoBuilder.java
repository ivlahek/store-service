package hr.ivlahek.sample.store.client.order;

public final class OrderItemDtoBuilder {
    private Long productId;
    private Integer quantity;

    private OrderItemDtoBuilder() {
    }

    public static OrderItemDto build(Long productId, Integer quantity) {
        return new OrderItemDtoBuilder().withProductId(productId).withQuantity(quantity).build();
    }


    public static OrderItemDtoBuilder anOrderItemDto() {
        return new OrderItemDtoBuilder();
    }

    public OrderItemDtoBuilder withProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public OrderItemDtoBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderItemDto build() {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(productId);
        orderItemDto.setQuantity(quantity);
        return orderItemDto;
    }
}
