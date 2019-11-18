package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.entity.ProductBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ProductRepositoryTest extends RepositoryTest {

    @Test
    public void should_save_product() {
        Product product = ProductBuilder.aProduct().build();

        //OPERATE
        productRepository.save(product);

        //CHECK
        assertThat(product.getId()).isNotNull();
    }

}