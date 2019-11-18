package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.exception.ConflictErrorException;
import hr.ivlahek.sample.store.exception.NotFoundException;
import hr.ivlahek.sample.store.exception.messages.ExceptionMessage;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.repository.ProductRepository;
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

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public Product create(CreateProductDto createProductDto) {
        logger.info("Create product {}", createProductDto);
        Optional<Product> optionalProduct = productRepository.findByName(createProductDto.getName());
        if (optionalProduct.isPresent()) {
            logger.error("Product with the provided name {} already exists!", createProductDto.getName());
            throw new ConflictErrorException(ExceptionMessage.PRODUCT_NAME_ALREADY_EXISTS);
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
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            logger.error("Product with the provided id {} does not exist!", productId);
            throw new NotFoundException(ExceptionMessage.PRODUCT_DOES_NOT_EXIST);
        }

        Optional<Product> productWithSameName = productRepository.findByName(createProductDto.getName());
        Product product = optionalProduct.get();
        if (productWithSameName.isPresent() && !Objects.equals(product.getId(), productWithSameName.get().getId())) {
            logger.error("Product with the provided name {} already exists!", createProductDto.getName());
            throw new ConflictErrorException(ExceptionMessage.PRODUCT_NAME_ALREADY_EXISTS);
        }

        return new ProductMapper().mapForUpdate(product, createProductDto);
    }
}
