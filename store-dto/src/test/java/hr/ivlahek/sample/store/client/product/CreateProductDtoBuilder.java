package hr.ivlahek.sample.store.client.product;

import java.math.BigDecimal;

public final class CreateProductDtoBuilder {
    private String name = EntityDefaults.Product1Defaults.NAME;
    private String sku = EntityDefaults.Product1Defaults.SKU;
    private BigDecimal price = BigDecimal.valueOf(EntityDefaults.Product1Defaults.PRICE);

    private CreateProductDtoBuilder() {
    }

    public static CreateProductDtoBuilder aCreateProductDto1() {
        return new CreateProductDtoBuilder();
    }

    public static CreateProductDtoBuilder aCreateProductDto2() {
        return new CreateProductDtoBuilder()
                .withSku(EntityDefaults.Product2Defaults.SKU)
                .withName(EntityDefaults.Product2Defaults.NAME)
                .withPrice(BigDecimal.valueOf(EntityDefaults.Product2Defaults.PRICE));
    }

    public static CreateProductDtoBuilder aCreateProductDto3() {
        return new CreateProductDtoBuilder()
                .withSku(EntityDefaults.Product3Defaults.SKU)
                .withName(EntityDefaults.Product3Defaults.NAME)
                .withPrice(BigDecimal.valueOf(EntityDefaults.Product3Defaults.PRICE));
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
