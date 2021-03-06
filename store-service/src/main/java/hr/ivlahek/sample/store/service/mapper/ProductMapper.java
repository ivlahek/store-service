package hr.ivlahek.sample.store.service.mapper;

import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.persistence.entity.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public ProductDto map(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductSku(product.getSku());
        productDto.setName(product.getName());
        productDto.setPrice(BigDecimal.valueOf(product.getPrice()));
        productDto.setId(product.getId());
        return productDto;
    }

    public Product map(CreateProductDto createProductDto) {
        Product product = new Product();
        mapCommon(product, createProductDto);
        product.setDateCreated(new Date());
        product.setCreated();
        return product;
    }

    private Product mapCommon(Product product, CreateProductDto createProductDto) {
        product.setSku(createProductDto.getSku());
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice().doubleValue());
        product.setDateUpdated(new Date());
        return product;
    }

    public List<ProductDto> map(List<Product> products) {
        return products
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public Product mapForUpdate(Product product, CreateProductDto createProductDto) {
        return mapCommon(product, createProductDto);
    }
}
