package hr.ivlahek.sample.store.service;

import hr.ivlahek.sample.store.client.order.OrderItemDto;
import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.*;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

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
        product2 = ProductBuilder.aProduct2().build();
        productRepository.save(product2);

        order1 = PlacedOrderBuilder.anOrder1().build();
        orderRepository.save(order1);

        orderItem1 = PlacedOrderProductBuilder
                .aPlacedOrderItem()
                .withProduct(product1)
                .withQuantity(10)
                .withPlacedOrder(order1).build();
        orderItemRepository.save(orderItem1);

        order2 = PlacedOrderBuilder.anOrder1().build();
        orderRepository.save(order2);

        orderItem2 = PlacedOrderProductBuilder.aPlacedOrderItem()
                .withProduct(product2)
                .withQuantity(20)
                .withPlacedOrder(order2).build();
        orderItemRepository.save(orderItem2);
    }

    @Test
    public void should_retrieve_grouped_orders() {
        Map<Long, List<OrderItemDto>> orderProductsPerPlacedOrder = orderItemService.findItemsPerOrder(Lists.newArrayList(order1, order2));

        assertThat(orderProductsPerPlacedOrder.get(order1.getId())).hasSize(1);
        assertThat(orderProductsPerPlacedOrder.get(order1.getId()).get(0).getQuantity()).isEqualTo(orderItem1.getQuantity());
        assertThat(orderProductsPerPlacedOrder.get(order1.getId()).get(0).getProductId()).isEqualTo(orderItem1.getProduct().getId());

        assertThat(orderProductsPerPlacedOrder.get(order2.getId())).hasSize(1);
        assertThat(orderProductsPerPlacedOrder.get(order2.getId()).get(0).getQuantity()).isEqualTo(orderItem2.getQuantity());
        assertThat(orderProductsPerPlacedOrder.get(order2.getId()).get(0).getProductId()).isEqualTo(orderItem2.getProduct().getId());
    }
}