package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.*;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class OrderItemServiceTest extends RepositoryTest {
    @Autowired
    private OrderItemService orderItemService;

    private Product product1;
    private Product product2;
    private Order order1;
    private Order order2;
    private OrderItem orderItem1;
    private OrderItem orderItem2;

    @Before
    public void setUp() throws Exception {
        product1 = ProductBuilder.aProduct1().build();
        productRepository.save(product1);
        product2 = ProductBuilder.aProduct2().deleted().build();
        productRepository.save(product2);

        order1 = OrderBuilder.anOrder1().build();
        orderRepository.save(order1);

        orderItem1 = OrderItemBuilder
                .aPlacedOrderItem()
                .withProduct(product1)
                .withQuantity(10)
                .withPlacedOrder(order1).build();
        orderItemRepository.save(orderItem1);

        order2 = OrderBuilder.anOrder1().build();
        orderRepository.save(order2);

        orderItem2 = OrderItemBuilder.aPlacedOrderItem()
                .withProduct(product2)
                .withQuantity(20)
                .withPlacedOrder(order2).build();
        orderItemRepository.save(orderItem2);

    }

    @Test
    public void should_retrieve_grouped_orders() {
        List<OrderItem> orderItemsList = orderItemService.findOrderItems(Lists.newArrayList(order1, order2));

        assertThat(orderItemsList).hasSize(2);
    }
}