package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.CreateProductDtoBuilder;
import hr.ivlahek.sample.store.exception.AppException;
import hr.ivlahek.sample.store.exception.messages.ExceptionMessage;
import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.entity.ProductBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class ProductServiceTest extends RepositoryTest {

    @Autowired
    private ProductService productService;

    @Test
    public void should_save_product() {
        CreateProductDto createProductDto = CreateProductDtoBuilder.aCreateProductDto().build();
        //OPERATE
        Product product = productService.create(createProductDto);

        //CHECK
        Optional<Product> createdProduct = productRepository.findById(product.getId());
        assertThat(createdProduct).isPresent();
    }

    @Test
    public void should_inform_product_sku_is_taken() {
        Product product = ProductBuilder.aProduct().build();
        productRepository.save(product);
        CreateProductDto createProductDto = CreateProductDtoBuilder.aCreateProductDto().withSku(product.getSku()).build();

        //OPERATE
        try {
            productService.create(createProductDto);
            failBecauseExceptionWasNotThrown(AppException.class);
        } catch (AppException e) {
            assertThat(e.getCode()).isEqualTo(ExceptionMessage.PRODUCT_SKU_ALREADY_EXISTS.getErrorCode());
        }
    }

    @Test
    public void should_get_paged() {
        Product product1 = ProductBuilder.aProduct1().build();
        productRepository.save(product1);
        Product product2 = ProductBuilder.aProduct2().build();
        productRepository.save(product2);

        Page<Product> page = productService.getProducts(PageRequest.of(0, 1));

        //check
        assertThat(page.getContent()).hasSize(1);
        Product actual = page.getContent().get(0);
        assertThat(actual.getId()).isEqualTo(product1.getId());
    }

}