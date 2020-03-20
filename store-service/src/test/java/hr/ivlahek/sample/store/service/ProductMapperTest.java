package hr.ivlahek.sample.store.service;

import hr.ivlahek.asserter.ProductAsserter;
import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.CreateProductDtoBuilder;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.entity.ProductBuilder;
import hr.ivlahek.sample.store.service.mapper.ProductMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;

public class ProductMapperTest {
    ProductMapper productMapper = new ProductMapper();

    @Test
    public void should_map_for_create() {
        CreateProductDto createProductDto = CreateProductDtoBuilder.aCreateProductDto().build();

        //OPERATE
        Product product = productMapper.map(createProductDto);

        //CHECK
        assertThat(product.getId()).isNull();
        ProductAsserter.assertProduct(product, createProductDto);
    }

    @Test
    public void should_map_product_dto() {
        Product product = ProductBuilder.aProduct().withId(1l).build();
        //OPERATE
        ProductDto productDto = productMapper.map(product);

        //CHECK
        ProductAsserter.assertProduct(product, productDto);
    }

    @Test
    public void should_map_product_page() {
        Product product1 = ProductBuilder.aProduct1().build();
        Product product2 = ProductBuilder.aProduct2().build();

        //OPERATE
        List<ProductDto> mappedProducts = productMapper.map(Lists.newArrayList(product1, product2));

        //OPERATE
        assertThat(mappedProducts).hasSize(2);
        assertThat(extractProperty("id").from(mappedProducts)).containsOnly(product1.getId(), product2.getId());
    }

    @Test
    public void should_map_for_update() {
        Product product1 = ProductBuilder.aProduct1().build();
        CreateProductDto createProductDto = CreateProductDtoBuilder.aCreateProductDto().withName("-1").withPrice(BigDecimal.valueOf(-1d)).withSku("-2").build();

        //OPERATE

        Product mappedProduct = productMapper.mapForUpdate(product1, createProductDto);

        //OPERATE
        ProductAsserter.assertProductForUpdate(mappedProduct, createProductDto);
    }

}