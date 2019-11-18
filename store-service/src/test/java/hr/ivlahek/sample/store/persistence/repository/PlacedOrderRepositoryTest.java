package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrder;
import hr.ivlahek.sample.store.persistence.entity.PlacedOrderBuilder;
import hr.ivlahek.sample.store.persistence.entity.Product;
import hr.ivlahek.sample.store.persistence.entity.ProductBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlacedOrderRepositoryTest extends RepositoryTest {

    private PlacedOrder placedOrder1;
    private PlacedOrder placedOrder2;

    @Before
    public void setUp() {
        placedOrder1 = PlacedOrderBuilder.anOrder1().build();
        placedOrderRepository.save(placedOrder1);
        placedOrder2 = PlacedOrderBuilder.anOrder2().build();
        placedOrderRepository.save(placedOrder2);
    }

    @Test
    public void should_return_orders_between_dates() {
        List<PlacedOrder> placedOrder = placedOrderRepository.findByDateCreatedBetween(
                Date.from(Instant.now().minusSeconds(50)),
                Date.from(Instant.now().plusSeconds(50)), Pageable.unpaged()).getContent();

        //CHECK
        assertThat(placedOrder).hasSize(1);
        assertThat(placedOrder.get(0).getId()).isEqualTo(placedOrder1.getId());
    }

    @Test
    public void should_return_orders_first_page_between_dates() {
        Page<PlacedOrder> page = placedOrderRepository.findByDateCreatedBetween(
                Date.from(Instant.now().minusSeconds(150)),
                Date.from(Instant.now().plusSeconds(150)), PageRequest.of(0, 1));
        List<PlacedOrder> placedOrder = page.getContent();

        //CHECK
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(placedOrder).hasSize(1);
        assertThat(placedOrder.get(0).getId()).isEqualTo(placedOrder1.getId());
    }

    @Test
    public void should_return_orders_second_page_between_dates() {
        Page<PlacedOrder> page = placedOrderRepository.findByDateCreatedBetween(
                Date.from(Instant.now().minusSeconds(150)),
                Date.from(Instant.now().plusSeconds(150)), PageRequest.of(1, 1));
        List<PlacedOrder> placedOrder = page.getContent();

        //CHECK
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(placedOrder).hasSize(1);
        assertThat(placedOrder.get(0).getId()).isEqualTo(placedOrder2.getId());
    }

    @Test
    public void should_create_order_with_product() {
        Product product = ProductBuilder.aProduct1().build();
        product = productRepository.save(product);
        PlacedOrder placedOrder = PlacedOrderBuilder.anOrder().build();

        //OPERATE
        placedOrderRepository.save(placedOrder);

        //OPERATE
        placedOrderRepository.save(placedOrder);

        //CHECK
        assertThat(placedOrder.getId()).isNotNull();
    }

}