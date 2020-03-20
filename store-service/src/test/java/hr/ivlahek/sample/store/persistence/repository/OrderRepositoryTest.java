package hr.ivlahek.sample.store.persistence.repository;

import hr.ivlahek.sample.store.persistence.RepositoryTest;
import hr.ivlahek.sample.store.persistence.entity.Order;
import hr.ivlahek.sample.store.persistence.entity.OrderBuilder;
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

public class OrderRepositoryTest extends RepositoryTest {

    private Order order1;
    private Order order2;

    @Before
    public void setUp() {
        order1 = OrderBuilder.anOrder1().build();
        orderRepository.save(order1);
        order2 = OrderBuilder.anOrder2().build();
        orderRepository.save(order2);
    }

    @Test
    public void should_return_orders_between_dates() {
        List<Order> order = orderRepository.findByDateCreatedBetween(
                Date.from(Instant.now().minusSeconds(50)),
                Date.from(Instant.now().plusSeconds(50)), Pageable.unpaged()).getContent();

        //CHECK
        assertThat(order).hasSize(1);
        assertThat(order.get(0).getId()).isEqualTo(order1.getId());
    }

    @Test
    public void should_return_orders_first_page_between_dates() {
        Page<Order> page = orderRepository.findByDateCreatedBetween(
                Date.from(Instant.now().minusSeconds(150)),
                Date.from(Instant.now().plusSeconds(150)), PageRequest.of(0, 1));
        List<Order> order = page.getContent();

        //CHECK
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(order).hasSize(1);
        assertThat(order.get(0).getId()).isEqualTo(order1.getId());
    }

    @Test
    public void should_return_orders_second_page_between_dates() {
        Page<Order> page = orderRepository.findByDateCreatedBetween(
                Date.from(Instant.now().minusSeconds(150)),
                Date.from(Instant.now().plusSeconds(150)), PageRequest.of(1, 1));
        List<Order> order = page.getContent();

        //CHECK
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(order).hasSize(1);
        assertThat(order.get(0).getId()).isEqualTo(order2.getId());
    }

    @Test
    public void should_create_order_with_product() {
        Product product = ProductBuilder.aProduct1().build();
        productRepository.save(product);
        Order order = OrderBuilder.anOrder().build();

        //OPERATE
        orderRepository.save(order);

        //OPERATE
        orderRepository.save(order);

        //CHECK
        assertThat(order.getId()).isNotNull();
    }

}