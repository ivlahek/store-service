package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.CreateOrderDtoBuilder;
import hr.ivlahek.sample.store.client.order.OrderItemDtoBuilder;
import hr.ivlahek.sample.store.exception.AppException;
import hr.ivlahek.sample.store.exception.messages.ExceptionMessage;
import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.Order;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.entity.ProductBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class OrderServiceTest extends RepositoryTest {

    @Autowired
    private OrderService orderService;
    private Product product1;
    private Product product2;

    @Before
    public void setUp() throws Exception {
        product1 = ProductBuilder.aProduct1().build();
        productRepository.save(product1);

        product2 = ProductBuilder.aProduct2().build();
        productRepository.save(product2);
    }

    @Test
    public void should_create_order_with_products() {
        CreateOrderDto createOrderDto = CreateOrderDtoBuilder.aCreateOrderDto()
                .addOrderItem(OrderItemDtoBuilder.build(product1.getId(), 1))
                .addOrderItem(OrderItemDtoBuilder.build(product2.getId(), 1))
                .build();

        //OPERATE
        Order order = orderService.placeOrder(createOrderDto);

        //CHECK
        order = orderRepository.findById(order.getId()).get();
        assertThat(order.getTotalPrice()).isEqualTo(7d);
        assertThat(order.getDateCreated()).isToday();
        assertThat(order.getEmail()).isEqualTo(createOrderDto.getEmail());
    }

    @Test
    public void should_inform_no_products_are_selected_in_order() {
        CreateOrderDto createOrderDto = CreateOrderDtoBuilder.aCreateOrderDto()
                .addOrderItem(OrderItemDtoBuilder.build(-1L, 1))
                .build();

        //OPERATE
        try {
            orderService.placeOrder(createOrderDto);
            failBecauseExceptionWasNotThrown(AppException.class);
        } catch (AppException e) {
            assertThat(e.getCode()).isEqualTo(ExceptionMessage.PRODUCT_CART_EMPTY.getErrorCode());
        }
    }

}