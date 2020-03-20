package hr.ivlahek.sample.store.client.order;

public final class CreateOrderItemDtoBuilder {
    private Long productId;
    private Integer quantity;

    private CreateOrderItemDtoBuilder() {
    }

    public static CreateOrderItemDto build(Long productId, Integer quantity) {
        return new CreateOrderItemDtoBuilder().withProductId(productId).withQuantity(quantity).build();
    }


    public static CreateOrderItemDtoBuilder anOrderItemDto() {
        return new CreateOrderItemDtoBuilder();
    }

    public CreateOrderItemDtoBuilder withProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public CreateOrderItemDtoBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public CreateOrderItemDto build() {
        CreateOrderItemDto orderItemDto = new CreateOrderItemDto();
        orderItemDto.setProductId(productId);
        orderItemDto.setQuantity(quantity);
        return orderItemDto;
    }
}
