package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.persistence.entity.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductMapper {
    public ProductDto map(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        return productDto;
    }

    public Product map(CreateProductDto createProductDto) {
        Product product = new Product();
        mapCommon(product, createProductDto);
        product.setDateCreated(new Date());
        return product;
    }

    private Product mapCommon(Product product, CreateProductDto createProductDto) {
        product.setDescription(createProductDto.getDescription());
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        product.setDateUpdated(new Date());
        return product;
    }

    public List<ProductDto> map(List<Product> products) {
        List<ProductDto> result = new ArrayList<>();
        products.forEach(product -> result.add(map(product)));
        return result;
    }

    public Product mapForUpdate(Product product, CreateProductDto createProductDto) {
        return mapCommon(product, createProductDto);
    }
}
