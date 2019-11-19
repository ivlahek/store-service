package hr.ivlahek.asserter;

import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.persistence.entity.Product;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductAsserter {

    public static void assertProductForUpdate(Product product, CreateProductDto createProductDto) {
        assertThat(product.getName()).isEqualTo(createProductDto.getName());
        assertThat(product.getPrice()).isEqualTo(createProductDto.getPrice().doubleValue());
        assertThat(product.getDescription()).isEqualTo(createProductDto.getDescription());
        assertThat(product.getDateUpdated()).isInSameMinuteAs(Date.from(Instant.now()));
    }

    public static void assertProduct(Product product, CreateProductDto createProductDto) {
        assertProductForUpdate(product, createProductDto);
        assertThat(product.getDateCreated()).isInSameHourAs(Date.from(Instant.now()));
    }


    public static void assertProduct(Product product, ProductDto productDto) {
        assertThat(product.getId()).isEqualTo(productDto.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo(productDto.getName()).isNotNull();
        assertThat(product.getPrice()).isEqualTo(productDto.getPrice().doubleValue()).isNotNull();
        assertThat(product.getDescription()).isEqualTo(productDto.getDescription()).isNotNull();
    }
}
