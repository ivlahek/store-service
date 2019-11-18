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


public class PlacedOrderItemServiceTest extends RepositoryTest {
    @Autowired
    private PlacedOrderItemService placedOrderItemService;

    private Product product1;
    private Product product2;
    private PlacedOrder placedOrder1;
    private PlacedOrder placedOrder2;
    private PlacedOrderItem placedOrderItem1;
    private PlacedOrderItem placedOrderItem2;

    @Before
    public void setUp() throws Exception {
        product1 = ProductBuilder.aProduct1().build();
        productRepository.save(product1);
        product2 = ProductBuilder.aProduct2().build();
        productRepository.save(product2);

        placedOrder1 = PlacedOrderBuilder.anOrder1().build();
        placedOrderRepository.save(placedOrder1);

        placedOrderItem1 = PlacedOrderProductBuilder
                .aPlacedOrderItem()
                .withProduct(product1)
                .withQuantity(10)
                .withPlacedOrder(placedOrder1).build();
        placedOrderItemRepository.save(placedOrderItem1);

        placedOrder2 = PlacedOrderBuilder.anOrder1().build();
        placedOrderRepository.save(placedOrder2);

        placedOrderItem2 = PlacedOrderProductBuilder.aPlacedOrderItem()
                .withProduct(product2)
                .withQuantity(20)
                .withPlacedOrder(placedOrder2).build();
        placedOrderItemRepository.save(placedOrderItem2);
    }

    @Test
    public void should_retrieve_grouped_orders() {
        Map<Long, List<OrderItemDto>> orderProductsPerPlacedOrder = placedOrderItemService.findOrderProductsPerPlacedOrder(Lists.newArrayList(placedOrder1, placedOrder2));

        assertThat(orderProductsPerPlacedOrder.get(placedOrder1.getId())).hasSize(1);
        assertThat(orderProductsPerPlacedOrder.get(placedOrder1.getId()).get(0).getQuantity()).isEqualTo(placedOrderItem1.getQuantity());
        assertThat(orderProductsPerPlacedOrder.get(placedOrder1.getId()).get(0).getProductId()).isEqualTo(placedOrderItem1.getProduct().getId());

        assertThat(orderProductsPerPlacedOrder.get(placedOrder2.getId())).hasSize(1);
        assertThat(orderProductsPerPlacedOrder.get(placedOrder2.getId()).get(0).getQuantity()).isEqualTo(placedOrderItem2.getQuantity());
        assertThat(orderProductsPerPlacedOrder.get(placedOrder2.getId()).get(0).getProductId()).isEqualTo(placedOrderItem2.getProduct().getId());
    }
}