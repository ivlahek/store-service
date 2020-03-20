package hr.ivlahek.sample.store.client.order;

import java.util.ArrayList;
import java.util.List;

public final class CreateOrderDtoBuilder {
    private String email = "john.doe@gmail.com";
    private List<CreateOrderItemDto> productList = new ArrayList<>();

    private CreateOrderDtoBuilder() {
    }

    public static CreateOrderDtoBuilder aCreateOrderDto() {
        return new CreateOrderDtoBuilder();
    }

    public CreateOrderDtoBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateOrderDtoBuilder withProductList(List<CreateOrderItemDto> productList) {
        this.productList = productList;
        return this;
    }

    public CreateOrderDtoBuilder addOrderItem(CreateOrderItemDto orderItemDto) {
        this.productList.add(orderItemDto);
        return this;
    }


    public CreateOrderDto build() {
        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setEmail(email);
        createOrderDto.setOrderItemDtos(productList);
        return createOrderDto;
    }
}
