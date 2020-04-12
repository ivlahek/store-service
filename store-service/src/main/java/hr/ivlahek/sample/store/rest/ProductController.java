package hr.ivlahek.sample.store.rest;

import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.rest.definition.ProductApiDefinition;
import hr.ivlahek.sample.store.service.ProductService;
import hr.ivlahek.sample.store.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Transactional
public class ProductController implements ProductApiDefinition {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

    public ProductDto createProduct(CreateProductDto createProductDto) {
        logger.info("Create product api called {}", createProductDto);
        return productService.create(createProductDto);
    }

    public ProductDto updateProduct(long productId, CreateProductDto createProductDto) {
        logger.info("Update product {} called {}", productId, createProductDto);
        return productService.update(productId, createProductDto);
    }

    public PageResponseDto<ProductDto> getAllProducts(@NotNull Pageable pageable) {
        logger.info("Create mobile application api called {}", pageable);
        return productService.getProducts(pageable);
    }

    @Override
    public void deleteProduct( long productId) {
        logger.info("Deleting product with id {}", productId);
        productService.deleteProduct(productId);
    }

}
