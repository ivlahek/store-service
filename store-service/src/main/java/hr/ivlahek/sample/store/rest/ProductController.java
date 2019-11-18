package hr.ivlahek.sample.store.rest;

import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.rest.definition.ProductApiDefinition;
import hr.ivlahek.sample.store.service.ProductMapper;
import hr.ivlahek.sample.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@Transactional
public class ProductController implements ProductApiDefinition {

    @Autowired
    private ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    public ProductDto createProduct(CreateProductDto createProductDto) {
        logger.info("Create product api called {}", createProductDto);
        return new ProductMapper().map(productService.create(createProductDto));
    }

    public ProductDto updateProduct(
            long productId,
            CreateProductDto createProductDto) {
        logger.info("Update product {} called {}", productId, createProductDto);
        return new ProductMapper().map(productService.update(productId, createProductDto));
    }

    public PageResponseDto<ProductDto> getAllProducts(
            @NotNull Pageable pageable) {
        logger.info("Create mobile application api called {}", pageable);
        Page<Product> products = productService.getProducts(pageable);
        return PageResponseDto.PageResponseDtoBuilder.aPageResponseDto()
                .withContent(new ProductMapper().map(products.getContent()))
                .withFirst(products.isFirst())
                .withLast(products.isLast())
                .withNextPage(products.hasNext())
                .withSize(products.getSize())
                .withTotalElements(products.getTotalElements())
                .withTotalPages(products.getTotalPages())
                .withPreviousPage(products.hasPrevious())
                .withPreviousPage(products.hasContent())
                .withNumber(products.getNumber())
                .withNumberOfElements(products.getNumberOfElements())
                .build();
    }

}
