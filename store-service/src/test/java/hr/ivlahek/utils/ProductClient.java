package hr.ivlahek.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.client.product.ProductResourceEndpoints;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class ProductClient {

    private TestRestTemplate testRestTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ProductClient(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public static String buildForUrl(String path, int page, int pageSize) {
        return UriComponentsBuilder.fromPath(path)
                .queryParam("page", page)
                .queryParam("size", pageSize).build().toString();
    }

    public List<ProductDto> getPaged(int page, int size) {
        List pageResponse = testRestTemplate.getForEntity(buildForUrl(ProductResourceEndpoints.PRODUCTS, page, size), PageResponseDto.class).getBody().getContent();
        return (List<ProductDto>) pageResponse.stream().map(object -> objectMapper.convertValue(object, ProductDto.class)).collect(Collectors.toList());
    }

    public ProductDto update(Long id, CreateProductDto createProductDto) {
        HttpEntity httpEntity = new HttpEntity(createProductDto);
        return testRestTemplate.exchange(ProductResourceEndpoints.PRODUCTS_BY_ID, HttpMethod.PUT, httpEntity, ProductDto.class, id).getBody();
    }

    public void deleteProduct(long productId) {
        testRestTemplate.delete(ProductResourceEndpoints.PRODUCTS_BY_ID, Long.valueOf(productId));

    }

    public ProductDto createProduct(CreateProductDto createProductDto) {
        return testRestTemplate.postForEntity(ProductResourceEndpoints.PRODUCTS, createProductDto, ProductDto.class).getBody();
    }
}
