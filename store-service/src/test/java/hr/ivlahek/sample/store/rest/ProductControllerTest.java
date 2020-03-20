package hr.ivlahek.sample.store.rest;

import hr.ivlahek.asserter.ProductAsserter;
import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.CreateProductDtoBuilder;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.client.validation.ValidationMessages;
import hr.ivlahek.sample.store.exception.messages.ExceptionMessage;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.entity.ProductBuilder;
import hr.ivlahek.utils.BadRequestAsserter;
import hr.ivlahek.utils.BadRequestAsserterFactory;
import hr.ivlahek.utils.ProductClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductControllerTest extends WebApiTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    private CreateProductDtoBuilder createProductDtoBuilder;
    private BadRequestAsserter badRequestAsserter;
    private ProductClient productClient;

    @Before
    public void setUp() {
        createProductDtoBuilder = CreateProductDtoBuilder.aCreateProductDto1();
        badRequestAsserter = BadRequestAsserterFactory.createForProductResource(testRestTemplate);
        productClient = new ProductClient(testRestTemplate);
    }

    @Test
    public void should_inform_product_name_is_null() {
        badRequestAsserter
                .executePost(createProductDtoBuilder.withName(null).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.PRODUCT_NAME_NULL);
    }

    @Test
    public void should_inform_product_name_is_empty() {
        badRequestAsserter
                .executePost(createProductDtoBuilder.withName("").build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.PRODUCT_NAME_EMPTY);
    }

    @Test
    public void should_inform_description_is_empty() {
        badRequestAsserter
                .executePost(createProductDtoBuilder.withSku("").build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.PRODUCT_SKU_EMPTY);
    }

    @Test
    public void should_inform_description_is_null() {
        badRequestAsserter
                .executePost(createProductDtoBuilder.withSku(null).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.PRODUCT_SKU_NULL);
    }

    @Test
    public void should_inform_price_is_null() {
        badRequestAsserter
                .executePost(createProductDtoBuilder.withPrice(null).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.PRODUCT_PRICE_NULL);
    }

    @Test
    public void should_inform_price_is_zero() {
        badRequestAsserter
                .executePost(createProductDtoBuilder.withPrice(BigDecimal.ZERO).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.PRODUCT_PRICE_NEGATIVE_OR_ZERO);
    }

    @Test
    public void should_inform_price_is_negative() {
        badRequestAsserter
                .executePost(createProductDtoBuilder.withPrice(BigDecimal.valueOf(-1d)).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.PRODUCT_PRICE_NEGATIVE_OR_ZERO);
    }


    @Test
    public void should_inform_product_can_not_be_found_when_trying_to_update() {
        BadRequestAsserterFactory.createForProductIdResource(testRestTemplate)
                .executePut(createProductDtoBuilder.build(), -1)
                .assertNotFound()
                .assertWithMessage(ExceptionMessage.PRODUCT_DOES_NOT_EXIST);
    }

    @Test
    public void should_create_product() {
        CreateProductDto createProductDto = createProductDtoBuilder.build();

        //OPERATE
        ProductDto productDto = productClient.createProduct(createProductDto);

        //CHECK
        Product product = productRepository.findById(productDto.getId()).get();

        ProductAsserter.assertProduct(product, createProductDto);
    }


    @Test
    public void should_inform_product_with_the_given_name_already_exists() {
        Product product = ProductBuilder.aProduct1().build();
        productRepository.save(product);

        badRequestAsserter.executePost(createProductDtoBuilder.withSku(product.getSku()).build())
                .assertConflict()
                .assertWithMessage(ExceptionMessage.PRODUCT_SKU_ALREADY_EXISTS);
    }

    @Test
    public void should_update_product() {
        Product product = ProductBuilder.aProduct1().build();
        productRepository.save(product);
        Product product2 = ProductBuilder.aProduct2().build();

        CreateProductDto createProductDto = createProductDtoBuilder
                .withName(product2.getName())
                .withSku(product2.getSku())
                .withPrice(BigDecimal.valueOf(product.getPrice())).build();

        //OPERATE
        ProductDto productDto = productClient.update(product.getId(), createProductDto);

        //CHECK
        product = productRepository.findById(productDto.getId()).get();
        ProductAsserter.assertProduct(product, createProductDto);
    }

    @Test
    public void should_inform_product_with_the_given_name_already_exists_when_trying_to_update_product() {
        Product product1 = ProductBuilder.aProduct1().build();
        productRepository.save(product1);
        Product product2 = ProductBuilder.aProduct2().build();
        productRepository.save(product2);

        CreateProductDto createProductDto = createProductDtoBuilder
                .withName(product2.getName())
                .withSku(product2.getSku())
                .withPrice(BigDecimal.valueOf(product1.getPrice())).build();

        //OPERATE
        BadRequestAsserterFactory.createForProductIdResource(testRestTemplate).executePut(createProductDto, product1.getId())
                .assertConflict()
                .assertWithMessage(ExceptionMessage.PRODUCT_SKU_ALREADY_EXISTS);
    }

    @Test
    public void should_get_products_paged__page1() {
        Product product1 = ProductBuilder.aProduct1().build();
        Product product2 = ProductBuilder.aProduct2().build();
        productRepository.save(product1);
        productRepository.save(product2);

        //OPERATE
        List<ProductDto> paged = productClient.getPaged(0, 1);

        //CHECK
        assertThat(paged).hasSize(1);
        assertThat(paged.get(0).getId()).isEqualTo(product1.getId());
    }

    @Test
    public void should_get_products_paged__pag2() {
        Product product1 = ProductBuilder.aProduct1().build();
        Product product2 = ProductBuilder.aProduct2().build();
        productRepository.save(product1);
        productRepository.save(product2);

        //OPERATE
        List<ProductDto> paged = productClient.getPaged(1, 1);

        //CHECK
        assertThat(paged).hasSize(1);
        assertThat(paged.get(0).getId()).isEqualTo(product2.getId());
    }

    @Test
    public void should_delete_product() {
        Product product1 = ProductBuilder.aProduct1().build();
        productRepository.save(product1);

        //OPERATE
        productClient.deleteProduct(product1.getId());

        //CHECK
        Optional<Product> optionalProduct = productRepository.findById(product1.getId());
        assertThat(optionalProduct).isNotPresent();

    }
}