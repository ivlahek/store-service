package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.exception.ConflictErrorException;
import hr.ivlahek.sample.store.exception.NotFoundException;
import hr.ivlahek.sample.store.exception.messages.ExceptionMessage;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.repository.ProductRepository;
import hr.ivlahek.sample.store.service.internal.InternalProductService;
import hr.ivlahek.sample.store.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InternalProductService internalProductService;

    public ProductDto create(CreateProductDto createProductDto) {
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
        return new ProductMapper().map(product);
    }

    public PageResponseDto<ProductDto> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
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

    public ProductDto update(long productId, CreateProductDto createProductDto) {
        Product product = findProductById(productId);

        Optional<Product> productWithSameName = productRepository.findBySku(createProductDto.getSku());
        if (productWithSameName.isPresent() && !Objects.equals(product.getId(), productWithSameName.get().getId())) {
            logger.error("Product with the provided name {} already exists!", createProductDto.getName());
            throw new ConflictErrorException(ExceptionMessage.PRODUCT_SKU_ALREADY_EXISTS);
        }
        product = new ProductMapper().mapForUpdate(product, createProductDto);
        productRepository.save(product);
        return new ProductMapper().map(product);
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
        productRepository.save(product);
    }
}
