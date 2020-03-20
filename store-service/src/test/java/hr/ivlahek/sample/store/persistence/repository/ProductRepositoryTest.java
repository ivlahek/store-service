package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.entity.ProductBuilder;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void should_not_fetch_deleted_product() {
        Product product = ProductBuilder.aProduct().build();
        product.setDeleted();
        productRepository.save(product);

        //OPERATE
        Optional<Product> optionalProduct = productRepository.findById(product.getId());

        //CHECK
        assertThat(optionalProduct).isNotPresent();
    }


    @Test
    public void should_fetch_non_deleted() {
        productRepository.save(ProductBuilder.aProduct1().build());

        //OPERATE
        List<Product> products = productRepository.findAll();

        //CHECK
        assertThat(products).hasSize(1);
    }

}