package hr.ivlahek.sample.store.rest;

import hr.ivlahek.asserter.OrderAsserter;
import hr.ivlahek.sample.store.client.order.*;
import hr.ivlahek.sample.store.client.validation.ValidationMessages;
import hr.ivlahek.sample.store.exception.messages.ExceptionMessage;
import hr.ivlahek.sample.store.persistence.entity.*;
import hr.ivlahek.utils.BadRequestAsserter;
import hr.ivlahek.utils.BadRequestAsserterFactory;
import hr.ivlahek.utils.OrderClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderControllerTest extends WebApiTest {
    private BadRequestAsserter orderBadRequestAsserter;
    private CreateOrderDtoBuilder createOrderDtoBuilder;
    private Product product1;
    private Product product2;
    @Autowired
    private OrderAsserter orderAsserter;
    private OrderClient orderClient;
    private Order order1;
    private Order order2;

    @Before
    public void setUp() {
        orderBadRequestAsserter = BadRequestAsserterFactory.createForOrderResource(testRestTemplate);
        createOrderDtoBuilder = CreateOrderDtoBuilder.aCreateOrderDto();

        product1 = ProductBuilder.aProduct1().build();
        productRepository.save(product1);
        product2 = ProductBuilder.aProduct2().build();
        productRepository.save(product2);

        orderClient = new OrderClient(testRestTemplate);

        order1 = PlacedOrderBuilder.anOrder1().build();
        orderRepository.save(order1);

        OrderItem orderItem1 = PlacedOrderProductBuilder
                .aPlacedOrderItem()
                .withProduct(product1)
                .withPlacedOrder(order1).build();
        orderItemRepository.save(orderItem1);

        order2 = PlacedOrderBuilder.anOrder1().build();
        orderRepository.save(order2);

        OrderItem orderItem2 = PlacedOrderProductBuilder.aPlacedOrderItem()
                .withProduct(product2)
                .withPlacedOrder(order2).build();
        orderItemRepository.save(orderItem2);
    }

    @Test
    public void should_inform_email_is_empty() {
        orderBadRequestAsserter
                .executePost(createOrderDtoBuilder.withEmail("").build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.ORDER_EMAIL_IS_EMPTY);
    }

    @Test
    public void should_inform_email_is_null() {
        orderBadRequestAsserter
                .executePost(createOrderDtoBuilder.withEmail(null).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.ORDER_EMAIL_IS_NULL);
    }

    @Test
    public void should_inform_email_is_not_a_valid_mail() {
        orderBadRequestAsserter
                .executePost(createOrderDtoBuilder.withEmail("not a valid mail").build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.ORDER_NOT_A_VALID_EMAIL);
    }

    @Test
    public void should_inform_product_list_is_null() {
        orderBadRequestAsserter
                .executePost(createOrderDtoBuilder.withProductList(null).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.ORDER_ITEM_LIST_NULL);
    }

    @Test
    public void should_inform_product_list_is_empty() {
        orderBadRequestAsserter
                .executePost(createOrderDtoBuilder.withProductList(new ArrayList<>()).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.ORDER_ITEM_LIST_EMPTY);
    }

    @Test
    public void should_inform_order_item_quantity_is_negative() {
        orderBadRequestAsserter
                .executePost(createOrderDtoBuilder.addOrderItem(OrderItemDtoBuilder.build(1L, -1)).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.ORDER_ITEM_QUANTITY_NEGATIVE_NUMBER);
    }

    @Test
    public void should_inform_order_item_list_is_empty() {
        orderBadRequestAsserter
                .executePost(createOrderDtoBuilder.addOrderItem(OrderItemDtoBuilder.build(-1L, 10)).build())
                .assertInternalServerError()
                .assertWithMessage(ExceptionMessage.PRODUCT_CART_EMPTY);
    }

    @Test
    public void should_inform_order_item_quantity_is_null() {
        orderBadRequestAsserter
                .executePost(createOrderDtoBuilder.addOrderItem(OrderItemDtoBuilder.build(1L, null)).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.ORDER_ITEM_QUANTITY_NULL);
    }

    @Test
    public void should_inform_order_item_product_id_is_null() {
        orderBadRequestAsserter
                .executePost(createOrderDtoBuilder.addOrderItem(OrderItemDtoBuilder.build(null, 1)).build())
                .assertBadRequest()
                .assertWithMessage(ValidationMessages.PRODUCT_ID_NULL);
    }

    @Test
    public void should_create_an_order() {
        CreateOrderDto createOrderDto = CreateOrderDtoBuilder.aCreateOrderDto()
                .addOrderItem(OrderItemDtoBuilder.build(product1.getId(), 3))
                .addOrderItem(OrderItemDtoBuilder.build(product2.getId(), 2))
                .addOrderItem(OrderItemDtoBuilder.build(-1L, 10))
                .build();

        //OPERATE
        OrderDto orderDto = testRestTemplate.postForEntity(OrderResourceEndpoints.ORDERS, createOrderDto, OrderDto.class).getBody();

        //CHECK
        orderAsserter.assertOrders(orderDto.getId(), createOrderDto);
        orderAsserter.assertAgainst(orderDto, orderDto.getId());
    }

    @Test
    public void should_create_an_order_with_same_products_as_a_separate_order_items() {
        CreateOrderDto createOrderDto = CreateOrderDtoBuilder.aCreateOrderDto()
                .addOrderItem(OrderItemDtoBuilder.build(product1.getId(), 3))
                .addOrderItem(OrderItemDtoBuilder.build(product2.getId(), 1))
                .addOrderItem(OrderItemDtoBuilder.build(product2.getId(), 1))
                .build();

        //OPERATE
        OrderDto orderDto = testRestTemplate.postForEntity(OrderResourceEndpoints.ORDERS, createOrderDto, OrderDto.class).getBody();

        //CHECK
        orderAsserter.assertOrders(orderDto.getId(), createOrderDto);
        orderAsserter.assertAgainst(orderDto, orderDto.getId());
    }


    @Test
    public void should_retrieve_order_in_a_date_range() {
        //OPERATE
        String dateFrom = DateTimeFormatter.ISO_DATE_TIME.format(Instant.now().minusSeconds(150).atZone(ZoneId.of("UTC")));
        String dateTo = DateTimeFormatter.ISO_DATE_TIME.format(Instant.now().plusSeconds(150).atZone(ZoneId.of("UTC")));
        List<OrderDto> page = orderClient.getPaged(0, 20, dateFrom, dateTo);

        //CHECK
        assertThat(page).hasSize(2);
        orderAsserter.assertAgainst(page.get(0), order1.getId());
        orderAsserter.assertAgainst(page.get(1), order2.getId());
    }

    @Test
    public void should_retrieve_order_in_a_date_range_first_page() {
        //OPERATE
        String dateFrom = DateTimeFormatter.ISO_DATE_TIME.format(Instant.now().minusSeconds(150).atZone(ZoneId.of("UTC")));
        String dateTo = DateTimeFormatter.ISO_DATE_TIME.format(Instant.now().plusSeconds(150).atZone(ZoneId.of("UTC")));
        List<OrderDto> page = orderClient.getPaged(0, 1, dateFrom, dateTo);

        //CHECK
        assertThat(page).hasSize(1);
        assertThat(page.get(0).getId()).isEqualTo(order1.getId());
        orderAsserter.assertAgainst(page.get(0), order1.getId());
    }

    @Test
    public void should_retrieve_order_in_a_date_range_second_page() {
        //OPERATE
        String dateFrom = DateTimeFormatter.ISO_DATE_TIME.format(Instant.now().minusSeconds(150).atZone(ZoneId.of("UTC")));
        String dateTo = DateTimeFormatter.ISO_DATE_TIME.format(Instant.now().plusSeconds(150).atZone(ZoneId.of("UTC")));
        List<OrderDto> page = orderClient.getPaged(1, 1, dateFrom, dateTo);

        //CHECK
        assertThat(page).hasSize(1);
        orderAsserter.assertAgainst(page.get(0), order2.getId());
    }
}