package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlacedOrderItemRepositoryTest extends RepositoryTest {

    @Test
    public void should_save_placed_order_product() {
        Product product = ProductBuilder.aProduct1().build();
        productRepository.save(product);
        PlacedOrder placedOrder = PlacedOrderBuilder.anOrder().build();
        placedOrderRepository.save(placedOrder);

        //OPERATE
        PlacedOrderItem placedOrderItem = PlacedOrderProductBuilder.aPlacedOrderItem().withPlacedOrder(placedOrder).withQuantity(2).withProduct(product).build();
        placedOrderItemRepository.save(placedOrderItem);

        //CHECK
        assertThat(placedOrderItem.getId()).isNotNull();
    }

}