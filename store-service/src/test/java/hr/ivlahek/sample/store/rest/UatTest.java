package hr.ivlahek.sample.store.rest;

import hr.ivlahek.sample.store.client.order.*;
import hr.ivlahek.sample.store.client.product.CreateProductDtoBuilder;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.utils.OrderClient;
import hr.ivlahek.utils.ProductClient;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UatTest extends WebApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ProductClient productClient;
    private OrderClient orderClient;

    @Before
    public void setUp() throws Exception {
        productClient = new ProductClient(testRestTemplate);
        orderClient = new OrderClient(testRestTemplate);
    }

    @Test
    public void uat_test() {
        //CREATE PRODUCTS
        ProductDto product1 = productClient.createProduct(CreateProductDtoBuilder.aCreateProductDto1().build());
        ProductDto product2 = productClient.createProduct(CreateProductDtoBuilder.aCreateProductDto2().build());

        //fetch products
        List<ProductDto> products = productClient.getPaged(0, 20);
        assertThat(products).hasSize(2);
        assertThat(product1).isEqualToComparingFieldByField(products.get(0));
        assertThat(product2).isEqualToComparingFieldByField(products.get(1));

        //CREATE ORDER
        List<CreateOrderItemDto> productList = Lists.list(
                CreateOrderItemDtoBuilder.build(product1.getId(), 1),
                CreateOrderItemDtoBuilder.build(product2.getId(), 2)
        );
        CreateOrderDto createOrderDto = CreateOrderDtoBuilder.aCreateOrderDto().withEmail("test@test")
                .withProductList(productList).build();
        OrderDto orderDto = orderClient.createOrder(createOrderDto);
        assertThat(orderDto.getTotalPrice().doubleValue()).isEqualTo(11.3);
        assertThat(orderDto.getEmail()).isEqualTo("test@test");
        assertThat(orderDto.getOrderItemDtos()).hasSize(2);

        //GET ORDER
        String dateFrom = DateTimeFormatter.ISO_DATE_TIME.format(Instant.now().minusSeconds(150).atZone(ZoneId.of("UTC")));
        String dateTo = DateTimeFormatter.ISO_DATE_TIME.format(Instant.now().plusSeconds(150).atZone(ZoneId.of("UTC")));
        List<OrderDto> orders = orderClient.getPaged(0, 20, dateFrom, dateTo);
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0)).isEqualToComparingFieldByField(orderDto);

        //DELETE PRODUCT
        productClient.deleteProduct(product1.getId());

        //GET PRODUCTS
        products = productClient.getPaged(0, 20);
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getId()).isEqualTo(product2.getId());

        //GET ORDER WITH DELETED PRODUCT
        orders = orderClient.getPaged(0, 20, dateFrom, dateTo);
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0)).isEqualToComparingFieldByField(orderDto);
    }
}
