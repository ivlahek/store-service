package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.exception.ConflictErrorException;
import hr.ivlahek.sample.store.exception.NotFoundException;
import hr.ivlahek.sample.store.exception.messages.ExceptionMessage;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.repository.ProductRepository;
import hr.ivlahek.sample.store.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    public Product create(CreateProductDto createProductDto) {
        logger.info("Create product {}", createProductDto);
        Optional<Product> optionalProduct = productRepository.findBySku(createProductDto.getSku());
        if (optionalProduct.isPresent()) {
            logger.error("Product with the provided name {} already exists!", createProductDto.getName());
            throw new ConflictErrorException(ExceptionMessage.PRODUCT_SKU_ALREADY_EXISTS);
        }
        Product product = new ProductMapper().map(createProductDto);
        logger.debug("Product mapped {}", product);
        productRepository.save(product);
        logger.debug("Product saved!");
        return product;
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> findByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

    public Product update(long productId, CreateProductDto createProductDto) {
        Product product = findProductById(productId);

        Optional<Product> productWithSameName = productRepository.findBySku(createProductDto.getSku());
        if (productWithSameName.isPresent() && !Objects.equals(product.getId(), productWithSameName.get().getId())) {
            logger.error("Product with the provided name {} already exists!", createProductDto.getName());
            throw new ConflictErrorException(ExceptionMessage.PRODUCT_SKU_ALREADY_EXISTS);
        }

        return new ProductMapper().mapForUpdate(product, createProductDto);
    }

    private Product findProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            logger.error("Product with the provided id {} does not exist!", productId);
            throw new NotFoundException(ExceptionMessage.PRODUCT_DOES_NOT_EXIST);
        }
        return optionalProduct.get();
    }

    public void deleteProduct(long productId) {
        Product product = findProductById(productId);
        product.setDeleted();
    }
}
