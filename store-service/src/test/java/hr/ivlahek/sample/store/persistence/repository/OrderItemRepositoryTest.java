package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderItemRepositoryTest extends RepositoryTest {

    @Test
    public void should_save_placed_order_product() {
        Product product = ProductBuilder.aProduct1().build();
        productRepository.save(product);
        Order order = PlacedOrderBuilder.anOrder().build();
        orderRepository.save(order);

        //OPERATE
        OrderItem orderItem = PlacedOrderProductBuilder.aPlacedOrderItem().withPlacedOrder(order).withQuantity(2).withProduct(product).build();
        orderItemRepository.save(orderItem);

        //CHECK
        assertThat(orderItem.getId()).isNotNull();
    }

}