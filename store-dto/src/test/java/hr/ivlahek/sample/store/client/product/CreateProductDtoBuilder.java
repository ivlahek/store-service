package hr.ivlahek.sample.store.client.product;

import java.math.BigDecimal;

public final class CreateProductDtoBuilder {
    private String name = "name";
    private String sku = "sku";
    private BigDecimal price = BigDecimal.valueOf(2.3d);

    private CreateProductDtoBuilder() {
    }

    public static CreateProductDtoBuilder aCreateProductDto() {
        return new CreateProductDtoBuilder();
    }

    public CreateProductDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateProductDtoBuilder withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public CreateProductDtoBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CreateProductDto build() {
        CreateProductDto createProductDto = new CreateProductDto();
        createProductDto.setName(name);
        createProductDto.setSku(sku);
        createProductDto.setPrice(price);
        return createProductDto;
    }
}
